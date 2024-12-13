package nl.jtepoel.AOC.utils.evaluator.antlr;// Generated from C:/Users/Jacco/Desktop/Projects/ProjectsJava/AOC/g.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class gLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, BOOL=20, TRUE=21, FALSE=22, AND=23, ID=24, CHAR=25, 
		DOUBLE=26, INT=27, WS=28;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
			"T__17", "T__18", "BOOL", "TRUE", "FALSE", "AND", "ID", "CHAR", "DOUBLE", 
			"INT", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'-'", "'+'", "'=='", "'!='", "'<='", "'<'", "'>='", "'>'", "'||'", 
			"'!'", "'*'", "'/'", "'('", "')'", "'^'", "'%'", "':INT'", "':DOUBLE'", 
			"':BOOL'", null, "'true'", "'false'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, "BOOL", "TRUE", "FALSE", 
			"AND", "ID", "CHAR", "DOUBLE", "INT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public gLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "g.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u001c\u00a6\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a"+
		"\u0002\u001b\u0007\u001b\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0013\u0001\u0013\u0003\u0013t\b\u0013\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0003\u0016\u0084\b\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0005"+
		"\u0017\u0089\b\u0017\n\u0017\f\u0017\u008c\t\u0017\u0001\u0018\u0001\u0018"+
		"\u0001\u0019\u0004\u0019\u0091\b\u0019\u000b\u0019\f\u0019\u0092\u0001"+
		"\u0019\u0001\u0019\u0004\u0019\u0097\b\u0019\u000b\u0019\f\u0019\u0098"+
		"\u0001\u001a\u0004\u001a\u009c\b\u001a\u000b\u001a\f\u001a\u009d\u0001"+
		"\u001b\u0004\u001b\u00a1\b\u001b\u000b\u001b\f\u001b\u00a2\u0001\u001b"+
		"\u0001\u001b\u0000\u0000\u001c\u0001\u0001\u0003\u0002\u0005\u0003\u0007"+
		"\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b"+
		"\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013"+
		"\'\u0014)\u0015+\u0016-\u0017/\u00181\u00193\u001a5\u001b7\u001c\u0001"+
		"\u0000\u0003\u0001\u000009\u0002\u0000AZaz\u0003\u0000\t\n\r\r  \u00ad"+
		"\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000"+
		"\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000"+
		"\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000"+
		"\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011"+
		"\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015"+
		"\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019"+
		"\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d"+
		"\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001"+
		"\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000"+
		"\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000"+
		"\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001\u0000\u0000\u0000\u0000/"+
		"\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000\u0000\u00003\u0001\u0000"+
		"\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u00007\u0001\u0000\u0000\u0000"+
		"\u00019\u0001\u0000\u0000\u0000\u0003;\u0001\u0000\u0000\u0000\u0005="+
		"\u0001\u0000\u0000\u0000\u0007@\u0001\u0000\u0000\u0000\tC\u0001\u0000"+
		"\u0000\u0000\u000bF\u0001\u0000\u0000\u0000\rH\u0001\u0000\u0000\u0000"+
		"\u000fK\u0001\u0000\u0000\u0000\u0011M\u0001\u0000\u0000\u0000\u0013P"+
		"\u0001\u0000\u0000\u0000\u0015R\u0001\u0000\u0000\u0000\u0017T\u0001\u0000"+
		"\u0000\u0000\u0019V\u0001\u0000\u0000\u0000\u001bX\u0001\u0000\u0000\u0000"+
		"\u001dZ\u0001\u0000\u0000\u0000\u001f\\\u0001\u0000\u0000\u0000!^\u0001"+
		"\u0000\u0000\u0000#c\u0001\u0000\u0000\u0000%k\u0001\u0000\u0000\u0000"+
		"\'s\u0001\u0000\u0000\u0000)u\u0001\u0000\u0000\u0000+z\u0001\u0000\u0000"+
		"\u0000-\u0083\u0001\u0000\u0000\u0000/\u0085\u0001\u0000\u0000\u00001"+
		"\u008d\u0001\u0000\u0000\u00003\u0090\u0001\u0000\u0000\u00005\u009b\u0001"+
		"\u0000\u0000\u00007\u00a0\u0001\u0000\u0000\u00009:\u0005-\u0000\u0000"+
		":\u0002\u0001\u0000\u0000\u0000;<\u0005+\u0000\u0000<\u0004\u0001\u0000"+
		"\u0000\u0000=>\u0005=\u0000\u0000>?\u0005=\u0000\u0000?\u0006\u0001\u0000"+
		"\u0000\u0000@A\u0005!\u0000\u0000AB\u0005=\u0000\u0000B\b\u0001\u0000"+
		"\u0000\u0000CD\u0005<\u0000\u0000DE\u0005=\u0000\u0000E\n\u0001\u0000"+
		"\u0000\u0000FG\u0005<\u0000\u0000G\f\u0001\u0000\u0000\u0000HI\u0005>"+
		"\u0000\u0000IJ\u0005=\u0000\u0000J\u000e\u0001\u0000\u0000\u0000KL\u0005"+
		">\u0000\u0000L\u0010\u0001\u0000\u0000\u0000MN\u0005|\u0000\u0000NO\u0005"+
		"|\u0000\u0000O\u0012\u0001\u0000\u0000\u0000PQ\u0005!\u0000\u0000Q\u0014"+
		"\u0001\u0000\u0000\u0000RS\u0005*\u0000\u0000S\u0016\u0001\u0000\u0000"+
		"\u0000TU\u0005/\u0000\u0000U\u0018\u0001\u0000\u0000\u0000VW\u0005(\u0000"+
		"\u0000W\u001a\u0001\u0000\u0000\u0000XY\u0005)\u0000\u0000Y\u001c\u0001"+
		"\u0000\u0000\u0000Z[\u0005^\u0000\u0000[\u001e\u0001\u0000\u0000\u0000"+
		"\\]\u0005%\u0000\u0000] \u0001\u0000\u0000\u0000^_\u0005:\u0000\u0000"+
		"_`\u0005I\u0000\u0000`a\u0005N\u0000\u0000ab\u0005T\u0000\u0000b\"\u0001"+
		"\u0000\u0000\u0000cd\u0005:\u0000\u0000de\u0005D\u0000\u0000ef\u0005O"+
		"\u0000\u0000fg\u0005U\u0000\u0000gh\u0005B\u0000\u0000hi\u0005L\u0000"+
		"\u0000ij\u0005E\u0000\u0000j$\u0001\u0000\u0000\u0000kl\u0005:\u0000\u0000"+
		"lm\u0005B\u0000\u0000mn\u0005O\u0000\u0000no\u0005O\u0000\u0000op\u0005"+
		"L\u0000\u0000p&\u0001\u0000\u0000\u0000qt\u0003)\u0014\u0000rt\u0003+"+
		"\u0015\u0000sq\u0001\u0000\u0000\u0000sr\u0001\u0000\u0000\u0000t(\u0001"+
		"\u0000\u0000\u0000uv\u0005t\u0000\u0000vw\u0005r\u0000\u0000wx\u0005u"+
		"\u0000\u0000xy\u0005e\u0000\u0000y*\u0001\u0000\u0000\u0000z{\u0005f\u0000"+
		"\u0000{|\u0005a\u0000\u0000|}\u0005l\u0000\u0000}~\u0005s\u0000\u0000"+
		"~\u007f\u0005e\u0000\u0000\u007f,\u0001\u0000\u0000\u0000\u0080\u0081"+
		"\u0005&\u0000\u0000\u0081\u0084\u0005&\u0000\u0000\u0082\u0084\u0005,"+
		"\u0000\u0000\u0083\u0080\u0001\u0000\u0000\u0000\u0083\u0082\u0001\u0000"+
		"\u0000\u0000\u0084.\u0001\u0000\u0000\u0000\u0085\u008a\u00031\u0018\u0000"+
		"\u0086\u0089\u00031\u0018\u0000\u0087\u0089\u0007\u0000\u0000\u0000\u0088"+
		"\u0086\u0001\u0000\u0000\u0000\u0088\u0087\u0001\u0000\u0000\u0000\u0089"+
		"\u008c\u0001\u0000\u0000\u0000\u008a\u0088\u0001\u0000\u0000\u0000\u008a"+
		"\u008b\u0001\u0000\u0000\u0000\u008b0\u0001\u0000\u0000\u0000\u008c\u008a"+
		"\u0001\u0000\u0000\u0000\u008d\u008e\u0007\u0001\u0000\u0000\u008e2\u0001"+
		"\u0000\u0000\u0000\u008f\u0091\u0007\u0000\u0000\u0000\u0090\u008f\u0001"+
		"\u0000\u0000\u0000\u0091\u0092\u0001\u0000\u0000\u0000\u0092\u0090\u0001"+
		"\u0000\u0000\u0000\u0092\u0093\u0001\u0000\u0000\u0000\u0093\u0094\u0001"+
		"\u0000\u0000\u0000\u0094\u0096\u0005.\u0000\u0000\u0095\u0097\u0007\u0000"+
		"\u0000\u0000\u0096\u0095\u0001\u0000\u0000\u0000\u0097\u0098\u0001\u0000"+
		"\u0000\u0000\u0098\u0096\u0001\u0000\u0000\u0000\u0098\u0099\u0001\u0000"+
		"\u0000\u0000\u00994\u0001\u0000\u0000\u0000\u009a\u009c\u0007\u0000\u0000"+
		"\u0000\u009b\u009a\u0001\u0000\u0000\u0000\u009c\u009d\u0001\u0000\u0000"+
		"\u0000\u009d\u009b\u0001\u0000\u0000\u0000\u009d\u009e\u0001\u0000\u0000"+
		"\u0000\u009e6\u0001\u0000\u0000\u0000\u009f\u00a1\u0007\u0002\u0000\u0000"+
		"\u00a0\u009f\u0001\u0000\u0000\u0000\u00a1\u00a2\u0001\u0000\u0000\u0000"+
		"\u00a2\u00a0\u0001\u0000\u0000\u0000\u00a2\u00a3\u0001\u0000\u0000\u0000"+
		"\u00a3\u00a4\u0001\u0000\u0000\u0000\u00a4\u00a5\u0006\u001b\u0000\u0000"+
		"\u00a58\u0001\u0000\u0000\u0000\t\u0000s\u0083\u0088\u008a\u0092\u0098"+
		"\u009d\u00a2\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}