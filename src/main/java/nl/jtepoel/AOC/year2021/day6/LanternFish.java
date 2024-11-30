package nl.jtepoel.AOC.year2021.day6;

import nl.jtepoel.AOC.utils.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LanternFish {
    public int timer;
    public int days;

    private HashMap<Point, Long> cache;

    public LanternFish(int timer, int days, HashMap<Point, Long> cache) {
        this.days = days;
        this.timer = timer;
        this.cache = cache;
    }

    public long simulate() {
        if (cache.containsKey(new Point(timer, days))) {
            return cache.get(new Point(timer, days));
        }

        int org_timer = timer;
        int org_days = days;


        List<LanternFish> fishes = new ArrayList<>();

        if (days > timer) {
            days-=timer;

            timer = 7;
            fishes.add(new LanternFish(9, days, cache));
        }

        while (days > timer) {
            days-=7;
            fishes.add(new LanternFish(9, days, cache));
        }



        long count = fishes.size();

        for (LanternFish fish : fishes) {
            count += fish.simulate();
        }


        cache.put(new Point(org_timer, org_days), count);

        return count;


    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setCache(HashMap<Point, Long> cache) {
        this.cache = cache;
    }
}
