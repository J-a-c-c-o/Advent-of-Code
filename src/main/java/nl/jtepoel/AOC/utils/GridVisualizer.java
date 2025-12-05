package nl.jtepoel.AOC.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * A GUI visualization tool for Grid objects.
 * Provides methods to create, update, and close a visual representation of a grid.
 *
 * @param <A> The type of elements stored in the grid
 */
public class GridVisualizer<A> {
    private JFrame frame;
    private JPanel panel;
    private Grid<A> grid;
    private int cellSize;
    private A defaultValue;
    private Function<A, Color> colorMapper;
    private Function<A, String> textMapper;
    private final Map<A, Color> colorCache;
    private boolean showText;
    private boolean fitToScreen;
    private double screenFraction;
    private boolean fadeEnabled;
    private int fadeDuration;
    private final Map<Point, FadeInfo> fadeMap;
    private final Map<Point, Color> previousColors;
    private BufferedImage buffer;
    private Color highlightColor;
    private double highlightPhaseRatio;
    private Timer animationTimer;
    private int targetFps = 25;

    private static class FadeInfo {
        Color fromColor;
        Color highlightColor;
        Color toColor;
        long startTime;
        int duration;
        double highlightPhaseRatio;

        FadeInfo(Color from, Color highlight, Color to, int duration, double highlightPhaseRatio) {
            this.fromColor = from;
            this.highlightColor = highlight;
            this.toColor = to;
            this.startTime = System.currentTimeMillis();
            this.duration = duration;
            this.highlightPhaseRatio = highlightPhaseRatio;
        }

        Color getCurrentColor() {
            long elapsed = System.currentTimeMillis() - startTime;
            if (elapsed >= duration) {
                return toColor;
            }

            int highlightEnd = (int) (duration * highlightPhaseRatio);

            if (elapsed < highlightEnd) {
                // Phase 1: fade from original to highlight
                float phase1Progress = (float) elapsed / highlightEnd;
                return interpolate(fromColor, highlightColor, phase1Progress);
            } else {
                // Phase 2: fade from highlight to target
                float phase2Progress = (float) (elapsed - highlightEnd) / (duration - highlightEnd);
                return interpolate(highlightColor, toColor, phase2Progress);
            }
        }

        boolean isComplete() {
            return System.currentTimeMillis() - startTime >= duration;
        }

        private static Color interpolate(Color c1, Color c2, float t) {
            t = Math.max(0, Math.min(1, t)); // Clamp to [0, 1]
            int r = (int) (c1.getRed() + t * (c2.getRed() - c1.getRed()));
            int g = (int) (c1.getGreen() + t * (c2.getGreen() - c1.getGreen()));
            int b = (int) (c1.getBlue() + t * (c2.getBlue() - c1.getBlue()));
            return new Color(r, g, b);
        }
    }

    /**
     * Creates a new GridVisualizer with default settings.
     */
    public GridVisualizer() {
        this.cellSize = 20;
        this.colorCache = new HashMap<>();
        this.showText = false;
        this.colorMapper = this::defaultColorMapper;
        this.textMapper = Object::toString;
        this.fitToScreen = false;
        this.screenFraction = 0.8;
        this.fadeEnabled = false;
        this.fadeDuration = 500;
        this.fadeMap = new ConcurrentHashMap<>();
        this.previousColors = new HashMap<>();
        this.highlightColor = Color.YELLOW;
        this.highlightPhaseRatio = 0.3;
    }

    /**
     * Creates and displays the visualization window for the given grid.
     *
     * @param grid         The grid to visualize
     * @param defaultValue The default value to display for empty cells
     */
    public void create(Grid<A> grid, A defaultValue) {
        create(grid, defaultValue, "Grid Visualization");
        startAnimationTimer();
    }

    /**
     * Creates and displays the visualization window for the given grid with a custom title.
     *
     * @param grid         The grid to visualize
     * @param defaultValue The default value to display for empty cells
     * @param title        The window title
     */
    public void create(Grid<A> grid, A defaultValue, String title) {
        this.grid = grid;
        this.defaultValue = defaultValue;
        // Calculate cell size to fit screen if enabled
        if (fitToScreen) {
            calculateFitCellSize();
        }

        int width = (grid.getMaxX() - grid.getMinX() + 1) * cellSize;
        int height = (grid.getMaxY() - grid.getMinY() + 1) * cellSize;

        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (buffer != null) {
                    g.drawImage(buffer, 0, 0, null);
                }
            }
        };

        // Enable double buffering
        panel.setDoubleBuffered(true);
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBackground(Color.BLACK);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Updates the visualization with the current state of the grid.
     */
    public void update() {
        if (frame == null || !frame.isVisible()) {
            return;
        }

        // Detect changes and trigger fades if fadeOnUpdate is enabled
        if (fadeEnabled) {
            detectChangesAndFade();
        }

        // Recalculate dimensions in case grid size changed
        int width = (grid.getMaxX() - grid.getMinX() + 1) * cellSize;
        int height = (grid.getMaxY() - grid.getMinY() + 1) * cellSize;

        // Recreate buffer if size changed
        if (buffer == null || buffer.getWidth() != width || buffer.getHeight() != height) {
            buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            panel.setPreferredSize(new Dimension(width, height));
            frame.pack();
        }

    }

    /**
     * Detects color changes in the grid and starts fade animations for changed cells.
     */
    private void detectChangesAndFade() {
        int minX = grid.getMinX();
        int minY = grid.getMinY();
        int maxX = grid.getMaxX();
        int maxY = grid.getMaxY();

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Point p = new Point(x, y);
                A value = grid.getOrDefault(x, y, defaultValue);
                Color currentColor = getColor(value);
                Color previousColor = previousColors.get(p);

                previousColors.put(p, currentColor);
                if (previousColor != null && !previousColor.equals(currentColor)) {
                    fadeMap.put(p, new FadeInfo(previousColor, highlightColor, currentColor, fadeDuration, highlightPhaseRatio));
                }
            }
        }

    }


    /**
     * Updates the visualization with a new grid.
     *
     * @param grid The new grid to visualize
     */
    public void update(Grid<A> grid) {
        this.grid = grid;
        update();
    }

    /**
     * Updates the visualization with a new grid and default value.
     *
     * @param grid         The new grid to visualize
     * @param defaultValue The new default value
     */
    public void update(Grid<A> grid, A defaultValue) {
        this.grid = grid;
        this.defaultValue = defaultValue;
        update();
    }

    /**
     * Closes the visualization window.
     */
    public void close() {
        stopAnimationTimer();
        if (frame != null) {
            frame.dispose();
            frame = null;
            panel = null;
        }
    }

    /**
     * Sets the cell size in pixels.
     *
     * @param cellSize The size of each cell in pixels
     * @return This visualizer for method chaining
     */
    public GridVisualizer<A> setCellSize(int cellSize) {
        this.cellSize = cellSize;
        return this;
    }

    /**
     * Sets a custom color mapper function.
     *
     * @param colorMapper A function that maps grid values to colors
     * @return This visualizer for method chaining
     */
    public GridVisualizer<A> setColorMapper(Function<A, Color> colorMapper) {
        this.colorMapper = colorMapper;
        return this;
    }

    /**
     * Sets a custom text mapper function.
     *
     * @param textMapper A function that maps grid values to display text
     * @return This visualizer for method chaining
     */
    public GridVisualizer<A> setTextMapper(Function<A, String> textMapper) {
        this.textMapper = textMapper;
        return this;
    }

    /**
     * Sets whether to show text in cells.
     *
     * @param showText True to show text, false to hide it
     * @return This visualizer for method chaining
     */
    public GridVisualizer<A> setShowText(boolean showText) {
        this.showText = showText;
        return this;
    }

    /**
     * Enables or disables automatic fitting to screen size.
     * When enabled, the cell size will be calculated to fit the grid within
     * the specified fraction of the screen.
     *
     * @param fitToScreen True to enable fit-to-screen, false to use fixed cell size
     * @return This visualizer for method chaining
     */
    public GridVisualizer<A> setFitToScreen(boolean fitToScreen) {
        this.fitToScreen = fitToScreen;
        return this;
    }

    /**
     * Sets the fraction of the screen to use when fitToScreen is enabled.
     *
     * @param screenFraction A value between 0.0 and 1.0 (default is 0.8)
     * @return This visualizer for method chaining
     */
    public GridVisualizer<A> setScreenFraction(double screenFraction) {
        this.screenFraction = Math.max(0.1, Math.min(1.0, screenFraction));
        return this;
    }

    /**
     * Calculates the optimal cell size to fit the grid within the screen.
     */
    private void calculateFitCellSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = (int) (screenSize.width * screenFraction);
        int maxHeight = (int) (screenSize.height * screenFraction);

        int gridWidth = grid.getMaxX() - grid.getMinX() + 1;
        int gridHeight = grid.getMaxY() - grid.getMinY() + 1;

        int cellByWidth = maxWidth / gridWidth;
        int cellByHeight = maxHeight / gridHeight;

        this.cellSize = Math.max(1, Math.min(cellByWidth, cellByHeight));
    }

    /**
     * Sets the duration of fade animations in milliseconds.
     *
     * @param fadeDuration The duration in milliseconds (default is 500)
     * @return This visualizer for method chaining
     */
    public GridVisualizer<A> setFadeDuration(int fadeDuration) {
        this.fadeDuration = fadeDuration;
        return this;
    }

    /**
     * Enables automatic fade detection on update.
     * When enabled, the visualizer will automatically detect color changes
     * and trigger fade animations when update() is called.
     *
     * @param fadeEnabled True to enable fade on update, false to disable
     * @return This visualizer for method chaining
     */
    public GridVisualizer<A> setFadeEnabled(boolean fadeEnabled) {
        this.fadeEnabled = fadeEnabled;
        return this;
    }

    /**
     * Sets the highlight color used during fade animations.
     * When a cell changes, it will first flash to this color before fading to the target.
     *
     * @param highlightColor The highlight color (default is Yellow)
     * @return This visualizer for method chaining
     */
    public GridVisualizer<A> setHighlightColor(Color highlightColor) {
        this.highlightColor = highlightColor;
        return this;
    }

    /**
     * Sets the ratio of the fade duration spent on the highlight phase.
     * For example, 0.3 means 30% of the fade time is spent transitioning to the highlight color,
     * and 70% is spent fading from highlight to the target color.
     *
     * @param ratio A value between 0.0 and 1.0 (default is 0.3)
     * @return This visualizer for method chaining
     */
    public GridVisualizer<A> setHighlightPhaseRatio(double ratio) {
        this.highlightPhaseRatio = Math.max(0.1, Math.min(0.9, ratio));
        return this;
    }

    /**
     * Starts a background animation thread that continuously updates the visualization.
     * This allows fade animations to run smoothly even when the main thread is sleeping.
     * Call this after create() to enable independent animation updates.
     *
     * @return This visualizer for method chaining
     */
    private GridVisualizer<A> startAnimationTimer() {
        if (animationTimer != null && animationTimer.isRunning()) {
            return this; // Already running
        }

        int delay = 1000 / targetFps;
        animationTimer = new Timer(delay, e -> {
            if (frame == null || !frame.isVisible()) {
                ((Timer) e.getSource()).stop();
                return;
            }

            if (hasFades()) {
                redrawBuffer();
            }
        });
        animationTimer.setRepeats(true);
        animationTimer.start();
        return this;
    }

    /**
     * Stops the background animation thread.
     */
    private void stopAnimationTimer() {
        if (animationTimer != null) {
            animationTimer.stop();
            animationTimer = null;
        }
    }

    /**
     * Sets the target frames per second for the animation thread.
     *
     * @param fps The target FPS (default is 60)
     * @return This visualizer for method chaining
     */
    public GridVisualizer<A> setTargetFps(int fps) {
        this.targetFps = Math.max(1, Math.min(120, fps));
        return this;
    }

    /**
     * Redraws the buffer (called from animation thread).
     */
    private void redrawBuffer() {
        if (buffer == null || grid == null) return;

        fadeMap.entrySet().removeIf(e -> e.getValue().isComplete());


        Graphics2D g2d = buffer.createGraphics();
        drawGrid(g2d);
        g2d.dispose();

        if (panel != null) {
            panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight());
        }
    }

    /**
     * Checks if there are any active fade animations.
     *
     * @return True if there are active fades
     */
    public boolean hasFades() {
        return !fadeMap.isEmpty();
    }

    /**
     * Waits for all fade animations to complete, updating the display periodically.
     */
    public void waitForFades() {
        while (hasFades()) {
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Adds a color mapping for a specific value.
     *
     * @param value The value to map
     * @param color The color to use for this value
     * @return This visualizer for method chaining
     */
    public GridVisualizer<A> addColor(A value, Color color) {
        colorCache.put(value, color);
        return this;
    }

    /**
     * Checks if the visualization window is still open.
     *
     * @return True if the window is open, false otherwise
     */
    public boolean isOpen() {
        return frame != null && frame.isVisible();
    }

    /**
     * Saves the current visualization to a BufferedImage.
     *
     * @return A BufferedImage of the current grid state
     */
    public BufferedImage toImage() {
        int width = (grid.getMaxX() - grid.getMinX() + 1) * cellSize;
        int height = (grid.getMaxY() - grid.getMinY() + 1) * cellSize;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        drawGrid(g);
        g.dispose();

        return image;
    }

    private void drawGrid(Graphics g) {
        if (grid == null) return;

        int minX = grid.getMinX();
        int minY = grid.getMinY();
        int maxX = grid.getMaxX();
        int maxY = grid.getMaxY();

        Graphics2D g2d = (Graphics2D) g;

        // Fill background with deep blue
        g2d.setColor(new Color(0, 0, 40));
        g2d.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());

        // Only enable antialiasing for text if cells are large enough
        if (showText && cellSize >= 12) {
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                A value = grid.getOrDefault(x, y, defaultValue);
                Point p = new Point(x, y);

                Color color;
                if (fadeEnabled && fadeMap.containsKey(p)) {
                    color = fadeMap.get(p).getCurrentColor();
                } else {
                    color = getColor(value);
                }

                int drawX = (x - minX) * cellSize;
                int drawY = (y - minY) * cellSize;

                // Fill cell
                g2d.setColor(color);
                g2d.fillRect(drawX, drawY, cellSize, cellSize);


                // Draw text
                if (showText && cellSize >= 12) {
                    String text = textMapper.apply(value);
                    if (text != null && !text.isEmpty()) {
                        // Truncate text if too long
                        if (text.length() > 2 && cellSize < 30) {
                            text = text.substring(0, 1);
                        }

                        g2d.setColor(getContrastColor(color));
                        Font font = new Font("Monospaced", Font.BOLD, Math.max(8, cellSize / 2));
                        g2d.setFont(font);
                        FontMetrics fm = g2d.getFontMetrics();
                        int textX = drawX + (cellSize - fm.stringWidth(text)) / 2;
                        int textY = drawY + (cellSize + fm.getAscent() - fm.getDescent()) / 2;
                        g2d.drawString(text, textX, textY);
                    }
                }
            }
        }
    }

    private Color getColor(A value) {
        if (colorCache.containsKey(value)) {
            return colorCache.get(value);
        }
        return colorMapper.apply(value);
    }

    private Color defaultColorMapper(A value) {
        if (value == null) {
            return new Color(0, 0, 40); // Deep blue for null
        }

        String str = value.toString();
        if (str.isEmpty()) {
            return new Color(0, 0, 40); // Deep blue for empty
        }

        // Common AOC character mappings
        char c = str.charAt(0);
        return switch (c) {
            case '#' -> Color.WHITE; // Enabled cells are white
            case '.' -> new Color(0, 0, 40); // Deep blue for empty/background
            case '@' -> Color.WHITE; // Enabled cells are white
            case 'O', 'o' -> Color.WHITE;
            case 'X', 'x' -> Color.RED;
            case 'S' -> Color.GREEN;
            case 'E' -> Color.RED;
            case '^', 'v', '<', '>' -> Color.CYAN;
            case '*' -> Color.ORANGE;
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> 
                new Color(50 + (c - '0') * 20, 100, 150 + (c - '0') * 10);
            default -> new Color(Math.abs(value.hashCode()) % 200 + 55,
                                Math.abs(value.hashCode() * 31) % 200 + 55,
                                Math.abs(value.hashCode() * 17) % 200 + 55);
        };
    }

    private Color getContrastColor(Color color) {
        // Calculate luminance and return black or white for best contrast
        double luminance = (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue()) / 255;
        return luminance > 0.5 ? Color.BLACK : Color.WHITE;
    }
}

