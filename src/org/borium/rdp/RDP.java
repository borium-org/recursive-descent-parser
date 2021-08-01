package org.borium.rdp;

import static org.borium.rdp.Arg.*;
import static org.borium.rdp.RdpAux.*;
import static org.borium.rdp.Scan.*;
import static org.borium.rdp.Symbol.*;
import static org.borium.rdp.Text.*;
import static org.borium.rdp.Text.TextMessageType.*;

import java.text.*;
import java.util.*;

public class RDP
{
	static final int RDP_TT_BOTTOM = 16/* SCAN_P_TOP */;
	static final int RDP_T_34 /* " */ = 16/* SCAN_P_TOP */;
	static final int RDP_T_35 /* # */ = 17;
	static final int RDP_T_39 /* ' */ = 18;
	static final int RDP_T_40 /* ( */ = 19;
	static final int RDP_T_4042 /* (* */ = 20;
	static final int RDP_T_41 /* ) */ = 21;
	static final int RDP_T_42 /* * */ = 22;
	static final int RDP_T_46 /* . */ = 23;
	static final int RDP_T_58 = 24;
	static final int RDP_T_5858 /* :: */ = 25;
	static final int RDP_T_585861 /* ::= */ = 26;
	static final int RDP_T_60 /* < */ = 27;
	static final int RDP_T_62 /* > */ = 28;
	static final int RDP_T_64 /* @ */ = 29;
	static final int RDP_T_ALT_ID = 30;
	static final int RDP_T_ANNOTATED_EPSILON_TREE = 31;
	static final int RDP_T_ARG_BLANK = 32;
	static final int RDP_T_ARG_BOOLEAN = 33;
	static final int RDP_T_ARG_NUMERIC = 34;
	static final int RDP_T_ARG_STRING = 35;
	static final int RDP_T_CASE_INSENSITIVE = 36;
	static final int RDP_T_CHAR = 37;
	static final int RDP_T_CHAR_ESC = 38;
	static final int RDP_T_COMMENT = 39;
	static final int RDP_T_COMMENT_LINE = 40;
	static final int RDP_T_COMMENT_LINE_VISIBLE = 41;
	static final int RDP_T_COMMENT_NEST = 42;
	static final int RDP_T_COMMENT_NEST_VISIBLE = 43;
	static final int RDP_T_COMMENT_VISIBLE = 44;
	static final int RDP_T_DERIVATION_TREE = 45;
	static final int RDP_T_EPSILON_TREE = 46;
	static final int RDP_T_GLOBAL = 47;
	static final int RDP_T_HASH_PRIME = 48;
	static final int RDP_T_HASH_SIZE = 49;
	static final int RDP_T_INCLUDE = 50;
	static final int RDP_T_INTERPRETER = 51;
	static final int RDP_T_MAX_ERRORS = 52;
	static final int RDP_T_MAX_WARNINGS = 53;
	static final int RDP_T_MULTIPLE_SOURCE_FILES = 54;
	static final int RDP_T_NEW_ID = 55;
	static final int RDP_T_NUMBER = 56;
	static final int RDP_T_OPTION = 57;
	static final int RDP_T_OUTPUT_FILE = 58;
	static final int RDP_T_PARSER = 59;
	static final int RDP_T_PASSES = 60;
	static final int RDP_T_POST_PARSE = 61;
	static final int RDP_T_POST_PROCESS = 62;
	static final int RDP_T_PRE_PARSE = 63;
	static final int RDP_T_PRE_PROCESS = 64;
	static final int RDP_T_RETAIN_COMMENTS = 65;
	static final int RDP_T_SET_SIZE = 66;
	static final int RDP_T_SHOW_SKIPS = 67;
	static final int RDP_T_STRING = 68;
	static final int RDP_T_STRING_ESC = 69;
	static final int RDP_T_SUFFIX = 70;
	static final int RDP_T_SUPPRESS_BUILT_IN_ARGUMENTS = 71;
	static final int RDP_T_SYMBOL_TABLE = 72;
	static final int RDP_T_TAB_WIDTH = 73;
	static final int RDP_T_TEXT_SIZE = 74;
	static final int RDP_T_TITLE = 75;
	static final int RDP_T_TREE = 76;
	static final int RDP_T_USES = 77;
	static final int RDP_T_91 /* [ */ = 78;
	static final int RDP_T_9142 /* [* */ = 79;
	static final int RDP_T_93 /* ] */ = 80;
	static final int RDP_T_94 = 91;
	static final int RDP_T_9494 = 92;
	static final int RDP_T_949494 /* ^^^ */ = 93;
	static final int RDP_T_9495 /* ^_ */ = 94;
	static final int RDP_T_123 /* { */ = 95;
	static final int RDP_T_124 /* | */ = 96;
	static final int RDP_T_125 /* } */ = 97;
	static final int RDP_TT_TOP = 98;

	private static final String __DATE__ = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
	private static final String __TIME__ = new SimpleDateFormat("HH:mm:ss").format(new Date());

	private static final String RDP_STAMP = "Generated on Sep 19 2015 11:45:00 and compiled on " + __DATE__ + " at "
			+ __TIME__;

	static String rdp_sourcefilename; // current source file name
	private static String[] rdp_sourcefilenames; // array of source file names

	private static Pointer<String> rdp_outputfilename = new Pointer<>("rdparser"); // output file name

	private static Pointer<Boolean> rdp_symbol_echo = new Pointer<>(false); // symbol echo flag
	private static Pointer<Boolean> rdp_verbose = new Pointer<>(false); // verbosity flag

	private static int rdp_sourcefilenumber;

	private static String[] rdp_tokens = { "IGNORE", "ID", "INTEGER", "REAL", "CHAR", "CHAR_ESC", "STRING",
			"STRING_ESC", "COMMENT", "COMMENT_VISIBLE", "COMMENT_NEST", "COMMENT_NEST_VISIBLE", "COMMENT_LINE",
			"COMMENT_LINE_VISIBLE", "EOF", "EOLN", "'\"'", "'#'", "'\''", "'('", "'(*'", "')'", "'*'", "'.'", "':'",
			"'::'", "'::='", "'<'", "'>'", "'@'", "'ALT_ID'", "'ANNOTATED_EPSILON_TREE'", "'ARG_BLANK'",
			"'ARG_BOOLEAN'", "'ARG_NUMERIC'", "'ARG_STRING'", "'CASE_INSENSITIVE'", "'CHAR'", "'CHAR_ESC'", "'COMMENT'",
			"'COMMENT_LINE'", "'COMMENT_LINE_VISIBLE'", "'COMMENT_NEST'", "'COMMENT_NEST_VISIBLE'", "'COMMENT_VISIBLE'",
			"'DERIVATION_TREE'", "'EPSILON_TREE'", "'GLOBAL'", "'HASH_PRIME'", "'HASH_SIZE'", "'INCLUDE'",
			"'INTERPRETER'", "'MAX_ERRORS'", "'MAX_WARNINGS'", "'MULTIPLE_SOURCE_FILES'", "'NEW_ID'", "'NUMBER'",
			"'OPTION'", "'OUTPUT_FILE'", "'PARSER'", "'PASSES'", "'POST_PARSE'", "'POST_PROCESS'", "'PRE_PARSE'",
			"'PRE_PROCESS'", "'RETAIN_COMMENTS'", "'SET_SIZE'", "'SHOW_SKIPS'", "'STRING'", "'STRING_ESC'", "'SUFFIX'",
			"'SUPPRESS_BUILT_IN_ARGUMENTS'", "'SYMBOL_TABLE'", "'TAB_WIDTH'", "'TEXT_SIZE'", "'TITLE'", "'TREE'",
			"'USES'", "'['", "'[*'", "']'", "'^'", "'^^'", "'^^^'", "'^_'", "'{'", "'|'", "'}'" };

	private static final Set String_stop = new Set();
	private static final Set code_stop = new Set();
	private static final Set comment_stop = new Set();
	private static final Set dir_first = new Set();
	private static final Set dir_stop = new Set();
	private static final Set item_com_first = new Set();
	private static final Set item_com_stop = new Set();
	private static final Set item_inl_first = new Set();
	private static final Set item_inl_stop = new Set();
	private static final Set item_ret_first = new Set();
	private static final Set item_ret_stop = new Set();
	private static final Set prod_first = new Set();
	private static final Set prod_stop = new Set();
	private static final Set rdp_dir_11_first = new Set();
	private static final Set rdp_dir_3_first = new Set();
	private static final Set rdp_dir_34_first = new Set();
	private static final Set rdp_dir_37_first = new Set();
	private static final Set rdp_dir_7_first = new Set();
	private static final Set rdp_item_inl_16_first = new Set();
	private static final Set rdp_item_inl_21_first = new Set();
	private static final Set rdp_item_inl_22_first = new Set();
	private static final Set rdp_item_inl_23_first = new Set();
	private static final Set rdp_item_inl_28_first = new Set();
	private static final Set rdp_item_inl_29_first = new Set();
	private static final Set rdp_item_inl_7_first = new Set();
	private static final Set rdp_item_inl_8_first = new Set();
	private static final Set rdp_item_inl_9_first = new Set();
	private static final Set rdp_item_ret_4_first = new Set();
	private static final Set rdp_item_ret_5_first = new Set();
	private static final Set rdp_item_ret_6_first = new Set();
	private static final Set rdp_prod_0_first = new Set();
	private static final Set rdp_prod_1_first = new Set();
	private static final Set rdp_prod_2_first = new Set();
	private static final Set rdp_rule_16_first = new Set();
	private static final Set rdp_seq_0_first = new Set();
	private static final Set rdp_seq_1_first = new Set();
	private static final Set rdp_seq_10_first = new Set();
	private static final Set rdp_seq_17_first = new Set();
	private static final Set rdp_seq_2_first = new Set();
	private static final Set rdp_seq_23_first = new Set();
	private static final Set rdp_seq_24_first = new Set();
	private static final Set rdp_seq_25_first = new Set();
	private static final Set rdp_seq_28_first = new Set();
	private static final Set rdp_seq_29_first = new Set();
	private static final Set rdp_seq_30_first = new Set();
	private static final Set rdp_seq_31_first = new Set();
	private static final Set rdp_seq_32_first = new Set();
	private static final Set rdp_seq_9_first = new Set();
	private static final Set rdp_unit_1_first = new Set();
	private static final Set rdp_unit_2_first = new Set();
	private static final Set rdp_unit_3_first = new Set();
	private static final Set rule_stop = new Set();
	private static final Set seq_first = new Set();
	private static final Set seq_stop = new Set();
	private static final Set token_stop = new Set();
	private static final Set unit_first = new Set();
	private static final Set unit_stop = new Set();

	@SuppressWarnings("unused")
	private static SymbolTable locals = null;
	static SymbolTable codes = null;
	static SymbolTable tokens = null;
	static SymbolTable rdp = null;

	public static void main(String[] args)
	{
		long rdp_start_time = System.currentTimeMillis();

		Pointer<Boolean> rdp_symbol_statistics = new Pointer<>(false); // show symbol_ table statistics flag
		// rdp_line_echo_all = 0, /* make a listing on all passes flag */
		Pointer<Boolean> rdp_filter = new Pointer<>(false); // filter flag
		Pointer<Boolean> rdp_line_echo = new Pointer<>(false); // make listing flag

		Pointer<Boolean> rdp_lexicalise = new Pointer<>(false); // print lexicalised output flag

		Pointer<Integer> rdp_textsize = new Pointer<>(60000); // size of scanner text array

		Pointer<Integer> rdp_tabwidth = new Pointer<>(8); // tab expansion width

		Pointer<String> rdp_vcg_filename = new Pointer<>(null); // filename for -V option

		// rdp_tree_node_data* rdp_tree = (rdp_tree_node_data*) graph_insert_graph("RDP derivation tree"); /* hook for
		// derivation tree */
		// rdp_tree_node_data* rdp_tree_root;

		arg_message("Recursive descent parser generator V1.65 (c) Adrian Johnstone 2000\n"
				+ "Ported to Java by Borium\n" + RDP_STAMP + "\n\n" + "Usage: rdparser [options] source[.bnf]");

		arg_message("");
		arg_boolean('f', "Filter mode (read from stdin and write to stdout)", rdp_filter);
		arg_boolean('l', "Make a listing", rdp_line_echo);
		arg_boolean('L', "Print lexicalised source file", rdp_lexicalise);
		arg_string('o', "Write output to filename", rdp_outputfilename);
		arg_boolean('s', "Echo each scanner symbol as it is read", rdp_symbol_echo);
		arg_boolean('S', "Print summary symbol table statistics", rdp_symbol_statistics);
		arg_numeric('t', "Tab expansion width (default " + rdp_tabwidth.value() + ")", rdp_tabwidth);
		arg_numeric('T', "Text buffer size in bytes for scanner (default " + rdp_textsize.value() + ")", rdp_textsize);
		arg_boolean('v', "Set verbose mode", rdp_verbose);
		arg_string('V', "Write derivation tree to filename in VCG format", rdp_vcg_filename);
		arg_message("");
		arg_boolean('e', "Write out expanded BNF along with first and follow sets", rdp_expanded);
		arg_boolean('E', "Add rule name to error messages in generated parser", rdp_error_production_name);
		arg_boolean('F', "Force creation of output files", rdp_force);
		arg_boolean('p', "Make parser only (omit semantic actions from generated code)", rdp_parser_only);
		arg_boolean('R', "Add rule entry and exit messages", rdp_trace);

		rdp_sourcefilenames = arg_process(args);

		// Fix up filetypes
		for (rdp_sourcefilenumber = 0; rdp_sourcefilenumber < rdp_sourcefilenames.length; rdp_sourcefilenumber++)
		{
			rdp_sourcefilenames[rdp_sourcefilenumber] = text_default_filetype(rdp_sourcefilenames[rdp_sourcefilenumber],
					"bnf");
		}

		if (rdp_filter.value())
		{
			rdp_sourcefilenames = new String[] { "-" };
			rdp_outputfilename.set("-");
		}

		if (rdp_sourcefilenames.length == 0)
			arg_help("no source files specified");
		rdp_sourcefilename = rdp_sourcefilenames[0];
		if (rdp_sourcefilenames.length != 1)
			text_message(TEXT_FATAL, "multiple source files not allowed\n");
		text_init(rdp_textsize.value(), 50, 120, rdp_tabwidth.value());
		scan_init(false, false, true, rdp_symbol_echo.value(), rdp_tokens);
		if (rdp_lexicalise.value())
			scan_lexicalise();
		locals = symbol_new_table("locals", 101, 31, new CompareHashPrint());
		codes = symbol_new_table("codes", 101, 31, new CompareHashPrint());
		tokens = symbol_new_table("tokens", 101, 31, new CompareHashPrint());
		rdp = symbol_new_table("rdp", 101, 31, new CompareHashPrint());
		rdp_set_initialise();
		rdp_load_keywords();
		rdp_pre_parse();
		// if (rdp_verbose)
		// text_printf("\nRecursive descent parser generator V1.65 (c) Adrian Johnstone 2000\n" RDP_STAMP "\n\n");
		// for (rdp_pass = 1; rdp_pass <= RDP_PASSES; rdp_pass++)
		// {
		// rdp_tree_update = rdp_pass == RDP_PASSES;
		// text_echo(rdp_line_echo_all || (rdp_line_echo && rdp_pass == RDP_PASSES));
		//
		// for (rdp_sourcefilenumber = 0; (rdp_sourcefilename = rdp_sourcefilenames[rdp_sourcefilenumber]) != NULL;
		// rdp_sourcefilenumber++)
		// {
		// if (text_open(rdp_sourcefilename) == NULL)
		// arg_help("unable to open source file");
		//
		// text_get_char();
		// scan_();
		//
		// unit(rdp_tree_root = rdp_add_node("unit", rdp_tree)); /* call parser at top level */
		// if (text_total_errors() != 0)
		// text_message(TEXT_FATAL, "error%s detected in source file 'pÿ'\n", text_total_errors() == 1 ? "" : "s",
		// rdp_sourcefilename); /* crash quietly */
		// graph_epsilon_prune_rdp_tree(rdp_tree_root, sizeof(rdp_tree_edge_data));
		// }
		// }
		//
		// rdp_sourcefilename = rdp_sourcefilenames[0]; /* Reset filename to first file in the list */
		//
		// graph_set_root(rdp_tree, rdp_tree_root);
		// if (rdp_vcg_filename != NULL)
		// {
		// FILE *rdp_vcg_file;
		//
		// if (*rdp_vcg_filename == '\0') /* No filename supplied */
		// rdp_vcg_filename = "rdparser";
		// rdp_vcg_file = fopen((rdp_vcg_filename = text_default_filetype(rdp_vcg_filename, "vcg")), "w");
		//
		// if (rdp_vcg_file == NULL)
		// text_message(TEXT_FATAL, "unable to open VCG file '%s' for write\n", rdp_vcg_filename);
		//
		// if (rdp_verbose)
		// text_message(TEXT_INFO, "Dumping derivation tree to VCG file '%s'\n", rdp_vcg_filename);
		//
		// text_redirect(rdp_vcg_file);
		// graph_vcg(rdp_tree, NULL, scan_vcg_print_node, scan_vcg_print_edge);
		// text_redirect(stdout);
		// fclose(rdp_vcg_file);
		// }
		//
		// rdp_post_parse(rdp_outputfilename, rdp_force);
		// if (rdp_symbol_statistics)
		// {
		// symbol_print_all_table_statistics(11);
		// symbol_print_all_table();
		//
		// }
		// text_print_total_errors();
		if (rdp_verbose.value() || true)
		{
			long rdp_finish_time = System.currentTimeMillis();
			System.out.println("Time: " + (double) (rdp_finish_time - rdp_start_time) / 1000);
		}
		// return rdp_error_return;
		throw new RuntimeException();
	}

	private static void rdp_load_keywords()
	{
		scan_load_keyword("\"", "\\", RDP_T_34 /* " */, SCAN_P_STRING_ESC);
		scan_load_keyword("#", null, RDP_T_35 /* # */, SCAN_P_IGNORE);
		scan_load_keyword("\'", "\\", RDP_T_39 /* ' */, SCAN_P_STRING_ESC);
		scan_load_keyword("(", null, RDP_T_40 /* ( */, SCAN_P_IGNORE);
		scan_load_keyword("(*", "*)", RDP_T_4042 /* (* */, SCAN_P_COMMENT);
		scan_load_keyword(")", null, RDP_T_41 /* ) */, SCAN_P_IGNORE);
		scan_load_keyword("*", null, RDP_T_42 /* * */, SCAN_P_IGNORE);
		scan_load_keyword(".", null, RDP_T_46 /* . */, SCAN_P_IGNORE);
		scan_load_keyword(":", null, RDP_T_58 /* : */, SCAN_P_IGNORE);
		scan_load_keyword("::", null, RDP_T_5858 /* :: */, SCAN_P_IGNORE);
		scan_load_keyword("::=", null, RDP_T_585861 /* ::= */, SCAN_P_IGNORE);
		scan_load_keyword("<", null, RDP_T_60 /* < */, SCAN_P_IGNORE);
		scan_load_keyword(">", null, RDP_T_62 /* > */, SCAN_P_IGNORE);
		scan_load_keyword("@", null, RDP_T_64 /* @ */, SCAN_P_IGNORE);
		scan_load_keyword("ALT_ID", null, RDP_T_ALT_ID, SCAN_P_IGNORE);
		scan_load_keyword("ANNOTATED_EPSILON_TREE", null, RDP_T_ANNOTATED_EPSILON_TREE, SCAN_P_IGNORE);
		scan_load_keyword("ARG_BLANK", null, RDP_T_ARG_BLANK, SCAN_P_IGNORE);
		scan_load_keyword("ARG_BOOLEAN", null, RDP_T_ARG_BOOLEAN, SCAN_P_IGNORE);
		scan_load_keyword("ARG_NUMERIC", null, RDP_T_ARG_NUMERIC, SCAN_P_IGNORE);
		scan_load_keyword("ARG_STRING", null, RDP_T_ARG_STRING, SCAN_P_IGNORE);
		scan_load_keyword("CASE_INSENSITIVE", null, RDP_T_CASE_INSENSITIVE, SCAN_P_IGNORE);
		scan_load_keyword("CHAR", null, RDP_T_CHAR, SCAN_P_IGNORE);
		scan_load_keyword("CHAR_ESC", null, RDP_T_CHAR_ESC, SCAN_P_IGNORE);
		scan_load_keyword("COMMENT", null, RDP_T_COMMENT, SCAN_P_IGNORE);
		scan_load_keyword("COMMENT_LINE", null, RDP_T_COMMENT_LINE, SCAN_P_IGNORE);
		scan_load_keyword("COMMENT_LINE_VISIBLE", null, RDP_T_COMMENT_LINE_VISIBLE, SCAN_P_IGNORE);
		scan_load_keyword("COMMENT_NEST", null, RDP_T_COMMENT_NEST, SCAN_P_IGNORE);
		scan_load_keyword("COMMENT_NEST_VISIBLE", null, RDP_T_COMMENT_NEST_VISIBLE, SCAN_P_IGNORE);
		scan_load_keyword("COMMENT_VISIBLE", null, RDP_T_COMMENT_VISIBLE, SCAN_P_IGNORE);
		scan_load_keyword("DERIVATION_TREE", null, RDP_T_DERIVATION_TREE, SCAN_P_IGNORE);
		scan_load_keyword("EPSILON_TREE", null, RDP_T_EPSILON_TREE, SCAN_P_IGNORE);
		scan_load_keyword("GLOBAL", null, RDP_T_GLOBAL, SCAN_P_IGNORE);
		scan_load_keyword("HASH_PRIME", null, RDP_T_HASH_PRIME, SCAN_P_IGNORE);
		scan_load_keyword("HASH_SIZE", null, RDP_T_HASH_SIZE, SCAN_P_IGNORE);
		scan_load_keyword("INCLUDE", null, RDP_T_INCLUDE, SCAN_P_IGNORE);
		scan_load_keyword("INTERPRETER", null, RDP_T_INTERPRETER, SCAN_P_IGNORE);
		scan_load_keyword("MAX_ERRORS", null, RDP_T_MAX_ERRORS, SCAN_P_IGNORE);
		scan_load_keyword("MAX_WARNINGS", null, RDP_T_MAX_WARNINGS, SCAN_P_IGNORE);
		scan_load_keyword("MULTIPLE_SOURCE_FILES", null, RDP_T_MULTIPLE_SOURCE_FILES, SCAN_P_IGNORE);
		scan_load_keyword("NEW_ID", null, RDP_T_NEW_ID, SCAN_P_IGNORE);
		scan_load_keyword("NUMBER", null, RDP_T_NUMBER, SCAN_P_IGNORE);
		scan_load_keyword("OPTION", null, RDP_T_OPTION, SCAN_P_IGNORE);
		scan_load_keyword("OUTPUT_FILE", null, RDP_T_OUTPUT_FILE, SCAN_P_IGNORE);
		scan_load_keyword("PARSER", null, RDP_T_PARSER, SCAN_P_IGNORE);
		scan_load_keyword("PASSES", null, RDP_T_PASSES, SCAN_P_IGNORE);
		scan_load_keyword("POST_PARSE", null, RDP_T_POST_PARSE, SCAN_P_IGNORE);
		scan_load_keyword("POST_PROCESS", null, RDP_T_POST_PROCESS, SCAN_P_IGNORE);
		scan_load_keyword("PRE_PARSE", null, RDP_T_PRE_PARSE, SCAN_P_IGNORE);
		scan_load_keyword("PRE_PROCESS", null, RDP_T_PRE_PROCESS, SCAN_P_IGNORE);
		scan_load_keyword("RETAIN_COMMENTS", null, RDP_T_RETAIN_COMMENTS, SCAN_P_IGNORE);
		scan_load_keyword("SET_SIZE", null, RDP_T_SET_SIZE, SCAN_P_IGNORE);
		scan_load_keyword("SHOW_SKIPS", null, RDP_T_SHOW_SKIPS, SCAN_P_IGNORE);
		scan_load_keyword("STRING", null, RDP_T_STRING, SCAN_P_IGNORE);
		scan_load_keyword("STRING_ESC", null, RDP_T_STRING_ESC, SCAN_P_IGNORE);
		scan_load_keyword("SUFFIX", null, RDP_T_SUFFIX, SCAN_P_IGNORE);
		scan_load_keyword("SUPPRESS_BUILT_IN_ARGUMENTS", null, RDP_T_SUPPRESS_BUILT_IN_ARGUMENTS, SCAN_P_IGNORE);
		scan_load_keyword("SYMBOL_TABLE", null, RDP_T_SYMBOL_TABLE, SCAN_P_IGNORE);
		scan_load_keyword("TAB_WIDTH", null, RDP_T_TAB_WIDTH, SCAN_P_IGNORE);
		scan_load_keyword("TEXT_SIZE", null, RDP_T_TEXT_SIZE, SCAN_P_IGNORE);
		scan_load_keyword("TITLE", null, RDP_T_TITLE, SCAN_P_IGNORE);
		scan_load_keyword("TREE", null, RDP_T_TREE, SCAN_P_IGNORE);
		scan_load_keyword("USES", null, RDP_T_USES, SCAN_P_IGNORE);
		scan_load_keyword("[", null, RDP_T_91 /* [ */, SCAN_P_IGNORE);
		scan_load_keyword("[*", "*]", RDP_T_9142 /* [* */, SCAN_P_COMMENT_VISIBLE);
		scan_load_keyword("]", null, RDP_T_93 /* ] */, SCAN_P_IGNORE);
		scan_load_keyword("^", null, RDP_T_94 /* ^ */, SCAN_P_IGNORE);
		scan_load_keyword("^^", null, RDP_T_9494 /* ^^ */, SCAN_P_IGNORE);
		scan_load_keyword("^^^", null, RDP_T_949494 /* ^^^ */, SCAN_P_IGNORE);
		scan_load_keyword("^_", null, RDP_T_9495 /* ^_ */, SCAN_P_IGNORE);
		scan_load_keyword("{", null, RDP_T_123 /* { */, SCAN_P_IGNORE);
		scan_load_keyword("|", null, RDP_T_124 /* | */, SCAN_P_IGNORE);
		scan_load_keyword("}", null, RDP_T_125 /* } */, SCAN_P_IGNORE);
	}

	private static void rdp_set_initialise()
	{
		String_stop.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, SCAN_P_EOF, RDP_T_34, RDP_T_39, RDP_T_40,
				RDP_T_41, RDP_T_46, RDP_T_58, RDP_T_60, RDP_T_62, RDP_T_ALT_ID, RDP_T_CHAR, RDP_T_CHAR_ESC,
				RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_LINE_VISIBLE, RDP_T_COMMENT_NEST,
				RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE, RDP_T_NEW_ID, RDP_T_NUMBER, RDP_T_STRING,
				RDP_T_STRING_ESC, RDP_T_91, RDP_T_9142, RDP_T_93, RDP_T_94, RDP_T_9494, RDP_T_949494, RDP_T_9495,
				RDP_T_123, RDP_T_124, RDP_T_125);
		code_stop.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, SCAN_P_EOF, RDP_T_34, RDP_T_39, RDP_T_40, RDP_T_41,
				RDP_T_46, RDP_T_58, RDP_T_60, RDP_T_62, RDP_T_64 /* @ */, RDP_T_ALT_ID, RDP_T_CHAR, RDP_T_CHAR_ESC,
				RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_LINE_VISIBLE, RDP_T_COMMENT_NEST,
				RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE, RDP_T_NEW_ID, RDP_T_NUMBER, RDP_T_STRING,
				RDP_T_STRING_ESC, RDP_T_91, RDP_T_9142, RDP_T_93, RDP_T_94, RDP_T_9494, RDP_T_949494, RDP_T_9495,
				RDP_T_123, RDP_T_124, RDP_T_125);
		comment_stop.assignList(SCAN_P_EOF);
		dir_first.assignList(RDP_T_ANNOTATED_EPSILON_TREE, RDP_T_ARG_BLANK, RDP_T_ARG_BOOLEAN, RDP_T_ARG_NUMERIC,
				RDP_T_ARG_STRING, RDP_T_CASE_INSENSITIVE, RDP_T_DERIVATION_TREE, RDP_T_EPSILON_TREE, RDP_T_GLOBAL,
				RDP_T_HASH_PRIME, RDP_T_HASH_SIZE, RDP_T_INCLUDE, RDP_T_INTERPRETER, RDP_T_MAX_ERRORS,
				RDP_T_MAX_WARNINGS, RDP_T_MULTIPLE_SOURCE_FILES, RDP_T_OPTION, RDP_T_OUTPUT_FILE, RDP_T_PARSER,
				RDP_T_PASSES, RDP_T_POST_PARSE, RDP_T_POST_PROCESS, RDP_T_PRE_PARSE, RDP_T_PRE_PROCESS,
				RDP_T_RETAIN_COMMENTS, RDP_T_SET_SIZE, RDP_T_SHOW_SKIPS, RDP_T_SUFFIX,
				RDP_T_SUPPRESS_BUILT_IN_ARGUMENTS, RDP_T_SYMBOL_TABLE, RDP_T_TAB_WIDTH, RDP_T_TEXT_SIZE, RDP_T_TITLE,
				RDP_T_TREE, RDP_T_USES);
		dir_stop.assignList(SCAN_P_ID, SCAN_P_EOF, RDP_T_ANNOTATED_EPSILON_TREE, RDP_T_ARG_BLANK, RDP_T_ARG_BOOLEAN,
				RDP_T_ARG_NUMERIC, RDP_T_ARG_STRING, RDP_T_CASE_INSENSITIVE, RDP_T_DERIVATION_TREE, RDP_T_EPSILON_TREE,
				RDP_T_GLOBAL, RDP_T_HASH_PRIME, RDP_T_HASH_SIZE, RDP_T_INCLUDE, RDP_T_INTERPRETER, RDP_T_MAX_ERRORS,
				RDP_T_MAX_WARNINGS, RDP_T_MULTIPLE_SOURCE_FILES, RDP_T_OPTION, RDP_T_OUTPUT_FILE, RDP_T_PARSER,
				RDP_T_PASSES, RDP_T_POST_PARSE, RDP_T_POST_PROCESS, RDP_T_PRE_PARSE, RDP_T_PRE_PROCESS,
				RDP_T_RETAIN_COMMENTS, RDP_T_SET_SIZE, RDP_T_SHOW_SKIPS, RDP_T_SUFFIX,
				RDP_T_SUPPRESS_BUILT_IN_ARGUMENTS, RDP_T_SYMBOL_TABLE, RDP_T_TAB_WIDTH, RDP_T_TEXT_SIZE, RDP_T_TITLE,
				RDP_T_TREE, RDP_T_USES);
		item_com_first.assignList(RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_NEST);
		item_com_stop.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, SCAN_P_EOF, RDP_T_34, RDP_T_39, RDP_T_40,
				RDP_T_41, RDP_T_46, RDP_T_58, RDP_T_60, RDP_T_62, RDP_T_ALT_ID, RDP_T_CHAR, RDP_T_CHAR_ESC,
				RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_LINE_VISIBLE, RDP_T_COMMENT_NEST,
				RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE, RDP_T_NEW_ID, RDP_T_NUMBER, RDP_T_STRING,
				RDP_T_STRING_ESC, RDP_T_91, RDP_T_9142, RDP_T_93, RDP_T_94, RDP_T_9494, RDP_T_949494, RDP_T_9495,
				RDP_T_123, RDP_T_124, RDP_T_125);
		item_inl_first.assignList(RDP_T_40, RDP_T_60, RDP_T_91, RDP_T_9142, RDP_T_123);
		item_inl_stop.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, SCAN_P_EOF, RDP_T_34, RDP_T_39, RDP_T_40,
				RDP_T_41, RDP_T_46, RDP_T_58, RDP_T_60, RDP_T_62, RDP_T_ALT_ID, RDP_T_CHAR, RDP_T_CHAR_ESC,
				RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_LINE_VISIBLE, RDP_T_COMMENT_NEST,
				RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE, RDP_T_NEW_ID, RDP_T_NUMBER, RDP_T_STRING,
				RDP_T_STRING_ESC, RDP_T_91, RDP_T_9142, RDP_T_93, RDP_T_123, RDP_T_124, RDP_T_125);
		item_ret_first.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, RDP_T_34, RDP_T_39, RDP_T_ALT_ID, RDP_T_CHAR,
				RDP_T_CHAR_ESC, RDP_T_COMMENT_LINE_VISIBLE, RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE,
				RDP_T_NEW_ID, RDP_T_NUMBER, RDP_T_STRING, RDP_T_STRING_ESC);
		item_ret_stop.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, SCAN_P_EOF, RDP_T_34, RDP_T_39, RDP_T_40,
				RDP_T_41, RDP_T_46, RDP_T_58, RDP_T_60, RDP_T_62, RDP_T_ALT_ID, RDP_T_CHAR, RDP_T_CHAR_ESC,
				RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_LINE_VISIBLE, RDP_T_COMMENT_NEST,
				RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE, RDP_T_NEW_ID, RDP_T_NUMBER, RDP_T_STRING,
				RDP_T_STRING_ESC, RDP_T_91, RDP_T_9142, RDP_T_93, RDP_T_94, RDP_T_9494, RDP_T_949494, RDP_T_9495,
				RDP_T_123, RDP_T_124, RDP_T_125);
		prod_first.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, RDP_T_34, RDP_T_39, RDP_T_40, RDP_T_60,
				RDP_T_ALT_ID, RDP_T_CHAR, RDP_T_CHAR_ESC, RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_LINE_VISIBLE,
				RDP_T_COMMENT_NEST, RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE, RDP_T_NEW_ID, RDP_T_NUMBER,
				RDP_T_STRING, RDP_T_STRING_ESC, RDP_T_91, RDP_T_9142, RDP_T_123);
		prod_stop.assignList(SCAN_P_EOF, RDP_T_41, RDP_T_46, RDP_T_62, RDP_T_93, RDP_T_125);
		rdp_dir_11_first.assignList(SCAN_P_ID, RDP_T_9142);
		rdp_dir_3_first.assignList(SCAN_P_ID, RDP_T_9142);
		rdp_dir_34_first.assignList(RDP_T_ANNOTATED_EPSILON_TREE, RDP_T_DERIVATION_TREE, RDP_T_EPSILON_TREE,
				RDP_T_SUPPRESS_BUILT_IN_ARGUMENTS, RDP_T_TREE);
		rdp_dir_37_first.assignList(RDP_T_ANNOTATED_EPSILON_TREE, RDP_T_DERIVATION_TREE, RDP_T_EPSILON_TREE,
				RDP_T_SUPPRESS_BUILT_IN_ARGUMENTS, RDP_T_TREE);
		rdp_dir_7_first.assignList(SCAN_P_ID, RDP_T_9142);
		rdp_item_inl_16_first.assignList(RDP_T_35, RDP_T_39);
		rdp_item_inl_21_first.assignList(RDP_T_94, RDP_T_9494, RDP_T_949494, RDP_T_9495);
		rdp_item_inl_22_first.assignList(SCAN_P_INTEGER, RDP_T_64);
		rdp_item_inl_23_first.assignList(SCAN_P_INTEGER, RDP_T_64);
		rdp_item_inl_28_first.assignList(RDP_T_40, RDP_T_60, RDP_T_91, RDP_T_9142, RDP_T_123);
		rdp_item_inl_29_first.assignList(RDP_T_40, RDP_T_60, RDP_T_91, RDP_T_9142, RDP_T_123);
		rdp_item_inl_7_first.assignList(RDP_T_94, RDP_T_9494, RDP_T_949494, RDP_T_9495);
		rdp_item_inl_8_first.assignList(RDP_T_94, RDP_T_9494, RDP_T_949494, RDP_T_9495);
		rdp_item_inl_9_first.assignList(RDP_T_94, RDP_T_9494, RDP_T_949494, RDP_T_9495);
		rdp_item_ret_4_first.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, RDP_T_34);
		rdp_item_ret_5_first.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, RDP_T_34);
		rdp_item_ret_6_first.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, RDP_T_34);
		rdp_prod_0_first.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, RDP_T_34, RDP_T_39, RDP_T_40, RDP_T_60,
				RDP_T_ALT_ID, RDP_T_CHAR, RDP_T_CHAR_ESC, RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_LINE_VISIBLE,
				RDP_T_COMMENT_NEST, RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE, RDP_T_NEW_ID, RDP_T_NUMBER,
				RDP_T_STRING, RDP_T_STRING_ESC, RDP_T_91, RDP_T_9142, RDP_T_123);
		rdp_prod_1_first.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, RDP_T_34, RDP_T_39, RDP_T_40, RDP_T_60,
				RDP_T_ALT_ID, RDP_T_CHAR, RDP_T_CHAR_ESC, RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_LINE_VISIBLE,
				RDP_T_COMMENT_NEST, RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE, RDP_T_NEW_ID, RDP_T_NUMBER,
				RDP_T_STRING, RDP_T_STRING_ESC, RDP_T_91, RDP_T_9142, RDP_T_123);
		rdp_prod_2_first.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, RDP_T_34, RDP_T_39, RDP_T_40, RDP_T_60,
				RDP_T_ALT_ID, RDP_T_CHAR, RDP_T_CHAR_ESC, RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_LINE_VISIBLE,
				RDP_T_COMMENT_NEST, RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE, RDP_T_NEW_ID, RDP_T_NUMBER,
				RDP_T_STRING, RDP_T_STRING_ESC, RDP_T_91, RDP_T_9142, RDP_T_123);
		rdp_rule_16_first.assignList(RDP_T_94, RDP_T_9494, RDP_T_949494, RDP_T_9495);
		rdp_seq_0_first.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, RDP_T_34, RDP_T_39, RDP_T_ALT_ID, RDP_T_CHAR,
				RDP_T_CHAR_ESC, RDP_T_COMMENT_LINE_VISIBLE, RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE,
				RDP_T_NEW_ID, RDP_T_NUMBER, RDP_T_STRING, RDP_T_STRING_ESC);
		rdp_seq_1_first.assignList(RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_NEST);
		rdp_seq_10_first.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, RDP_T_34, RDP_T_39, RDP_T_ALT_ID,
				RDP_T_CHAR, RDP_T_CHAR_ESC, RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_LINE_VISIBLE,
				RDP_T_COMMENT_NEST, RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE, RDP_T_NEW_ID, RDP_T_NUMBER,
				RDP_T_STRING, RDP_T_STRING_ESC);
		rdp_seq_17_first.assignList(RDP_T_94, RDP_T_9494, RDP_T_949494, RDP_T_9495);
		rdp_seq_2_first.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, RDP_T_34, RDP_T_39, RDP_T_ALT_ID, RDP_T_CHAR,
				RDP_T_CHAR_ESC, RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_LINE_VISIBLE, RDP_T_COMMENT_NEST,
				RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE, RDP_T_NEW_ID, RDP_T_NUMBER, RDP_T_STRING,
				RDP_T_STRING_ESC);
		rdp_seq_23_first.assignList(RDP_T_94, RDP_T_9494, RDP_T_949494, RDP_T_9495);
		rdp_seq_24_first.assignList(RDP_T_94, RDP_T_9494, RDP_T_949494, RDP_T_9495);
		rdp_seq_25_first.assignList(RDP_T_9142, RDP_T_94, RDP_T_9494, RDP_T_949494, RDP_T_9495);
		rdp_seq_28_first.assignList(RDP_T_40, RDP_T_60, RDP_T_91, RDP_T_9142, RDP_T_123);
		rdp_seq_29_first.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, RDP_T_34, RDP_T_39, RDP_T_40, RDP_T_60,
				RDP_T_ALT_ID, RDP_T_CHAR, RDP_T_CHAR_ESC, RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_LINE_VISIBLE,
				RDP_T_COMMENT_NEST, RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE, RDP_T_NEW_ID, RDP_T_NUMBER,
				RDP_T_STRING, RDP_T_STRING_ESC, RDP_T_91, RDP_T_9142, RDP_T_123);
		rdp_seq_30_first.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, RDP_T_34, RDP_T_39, RDP_T_40, RDP_T_60,
				RDP_T_ALT_ID, RDP_T_CHAR, RDP_T_CHAR_ESC, RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_LINE_VISIBLE,
				RDP_T_COMMENT_NEST, RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE, RDP_T_NEW_ID, RDP_T_NUMBER,
				RDP_T_STRING, RDP_T_STRING_ESC, RDP_T_91, RDP_T_9142, RDP_T_123);
		rdp_seq_31_first.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, RDP_T_34, RDP_T_39, RDP_T_40, RDP_T_60,
				RDP_T_ALT_ID, RDP_T_CHAR, RDP_T_CHAR_ESC, RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_LINE_VISIBLE,
				RDP_T_COMMENT_NEST, RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE, RDP_T_NEW_ID, RDP_T_NUMBER,
				RDP_T_STRING, RDP_T_STRING_ESC, RDP_T_91, RDP_T_9142, RDP_T_123);
		rdp_seq_32_first.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, RDP_T_34, RDP_T_39, RDP_T_40, RDP_T_60,
				RDP_T_ALT_ID, RDP_T_CHAR, RDP_T_CHAR_ESC, RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_LINE_VISIBLE,
				RDP_T_COMMENT_NEST, RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE, RDP_T_NEW_ID, RDP_T_NUMBER,
				RDP_T_STRING, RDP_T_STRING_ESC, RDP_T_91, RDP_T_9142, RDP_T_123);
		rdp_seq_9_first.assignList(RDP_T_94, RDP_T_9494, RDP_T_949494, RDP_T_9495);
		rdp_unit_1_first.assignList(RDP_T_ANNOTATED_EPSILON_TREE, RDP_T_ARG_BLANK, RDP_T_ARG_BOOLEAN, RDP_T_ARG_NUMERIC,
				RDP_T_ARG_STRING, RDP_T_CASE_INSENSITIVE, RDP_T_DERIVATION_TREE, RDP_T_EPSILON_TREE, RDP_T_GLOBAL,
				RDP_T_HASH_PRIME, RDP_T_HASH_SIZE, RDP_T_INCLUDE, RDP_T_INTERPRETER, RDP_T_MAX_ERRORS,
				RDP_T_MAX_WARNINGS, RDP_T_MULTIPLE_SOURCE_FILES, RDP_T_OPTION, RDP_T_OUTPUT_FILE, RDP_T_PARSER,
				RDP_T_PASSES, RDP_T_POST_PARSE, RDP_T_POST_PROCESS, RDP_T_PRE_PARSE, RDP_T_PRE_PROCESS,
				RDP_T_RETAIN_COMMENTS, RDP_T_SET_SIZE, RDP_T_SHOW_SKIPS, RDP_T_SUFFIX,
				RDP_T_SUPPRESS_BUILT_IN_ARGUMENTS, RDP_T_SYMBOL_TABLE, RDP_T_TAB_WIDTH, RDP_T_TEXT_SIZE, RDP_T_TITLE,
				RDP_T_TREE, RDP_T_USES);
		rdp_unit_2_first.assignList(SCAN_P_ID, RDP_T_ANNOTATED_EPSILON_TREE, RDP_T_ARG_BLANK, RDP_T_ARG_BOOLEAN,
				RDP_T_ARG_NUMERIC, RDP_T_ARG_STRING, RDP_T_CASE_INSENSITIVE, RDP_T_DERIVATION_TREE, RDP_T_EPSILON_TREE,
				RDP_T_GLOBAL, RDP_T_HASH_PRIME, RDP_T_HASH_SIZE, RDP_T_INCLUDE, RDP_T_INTERPRETER, RDP_T_MAX_ERRORS,
				RDP_T_MAX_WARNINGS, RDP_T_MULTIPLE_SOURCE_FILES, RDP_T_OPTION, RDP_T_OUTPUT_FILE, RDP_T_PARSER,
				RDP_T_PASSES, RDP_T_POST_PARSE, RDP_T_POST_PROCESS, RDP_T_PRE_PARSE, RDP_T_PRE_PROCESS,
				RDP_T_RETAIN_COMMENTS, RDP_T_SET_SIZE, RDP_T_SHOW_SKIPS, RDP_T_SUFFIX,
				RDP_T_SUPPRESS_BUILT_IN_ARGUMENTS, RDP_T_SYMBOL_TABLE, RDP_T_TAB_WIDTH, RDP_T_TEXT_SIZE, RDP_T_TITLE,
				RDP_T_TREE, RDP_T_USES);
		rdp_unit_3_first.assignList(SCAN_P_ID, RDP_T_ANNOTATED_EPSILON_TREE, RDP_T_ARG_BLANK, RDP_T_ARG_BOOLEAN,
				RDP_T_ARG_NUMERIC, RDP_T_ARG_STRING, RDP_T_CASE_INSENSITIVE, RDP_T_DERIVATION_TREE, RDP_T_EPSILON_TREE,
				RDP_T_GLOBAL, RDP_T_HASH_PRIME, RDP_T_HASH_SIZE, RDP_T_INCLUDE, RDP_T_INTERPRETER, RDP_T_MAX_ERRORS,
				RDP_T_MAX_WARNINGS, RDP_T_MULTIPLE_SOURCE_FILES, RDP_T_OPTION, RDP_T_OUTPUT_FILE, RDP_T_PARSER,
				RDP_T_PASSES, RDP_T_POST_PARSE, RDP_T_POST_PROCESS, RDP_T_PRE_PARSE, RDP_T_PRE_PROCESS,
				RDP_T_RETAIN_COMMENTS, RDP_T_SET_SIZE, RDP_T_SHOW_SKIPS, RDP_T_SUFFIX,
				RDP_T_SUPPRESS_BUILT_IN_ARGUMENTS, RDP_T_SYMBOL_TABLE, RDP_T_TAB_WIDTH, RDP_T_TEXT_SIZE, RDP_T_TITLE,
				RDP_T_TREE, RDP_T_USES);
		rule_stop.assignList(SCAN_P_ID, SCAN_P_EOF, RDP_T_ANNOTATED_EPSILON_TREE, RDP_T_ARG_BLANK, RDP_T_ARG_BOOLEAN,
				RDP_T_ARG_NUMERIC, RDP_T_ARG_STRING, RDP_T_CASE_INSENSITIVE, RDP_T_DERIVATION_TREE, RDP_T_EPSILON_TREE,
				RDP_T_GLOBAL, RDP_T_HASH_PRIME, RDP_T_HASH_SIZE, RDP_T_INCLUDE, RDP_T_INTERPRETER, RDP_T_MAX_ERRORS,
				RDP_T_MAX_WARNINGS, RDP_T_MULTIPLE_SOURCE_FILES, RDP_T_OPTION, RDP_T_OUTPUT_FILE, RDP_T_PARSER,
				RDP_T_PASSES, RDP_T_POST_PARSE, RDP_T_POST_PROCESS, RDP_T_PRE_PARSE, RDP_T_PRE_PROCESS,
				RDP_T_RETAIN_COMMENTS, RDP_T_SET_SIZE, RDP_T_SHOW_SKIPS, RDP_T_SUFFIX,
				RDP_T_SUPPRESS_BUILT_IN_ARGUMENTS, RDP_T_SYMBOL_TABLE, RDP_T_TAB_WIDTH, RDP_T_TEXT_SIZE, RDP_T_TITLE,
				RDP_T_TREE, RDP_T_USES);
		seq_first.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, RDP_T_34, RDP_T_39, RDP_T_40, RDP_T_60,
				RDP_T_ALT_ID, RDP_T_CHAR, RDP_T_CHAR_ESC, RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_LINE_VISIBLE,
				RDP_T_COMMENT_NEST, RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE, RDP_T_NEW_ID, RDP_T_NUMBER,
				RDP_T_STRING, RDP_T_STRING_ESC, RDP_T_91, RDP_T_9142, RDP_T_123);
		seq_stop.assignList(SCAN_P_EOF, RDP_T_41, RDP_T_46, RDP_T_62, RDP_T_93, RDP_T_124, RDP_T_125);
		token_stop.assignList(SCAN_P_ID, SCAN_P_INTEGER, SCAN_P_REAL, SCAN_P_EOF, RDP_T_34, RDP_T_39, RDP_T_40,
				RDP_T_41, RDP_T_46, RDP_T_58, RDP_T_60, RDP_T_62, RDP_T_ALT_ID, RDP_T_CHAR, RDP_T_CHAR_ESC,
				RDP_T_COMMENT, RDP_T_COMMENT_LINE, RDP_T_COMMENT_LINE_VISIBLE, RDP_T_COMMENT_NEST,
				RDP_T_COMMENT_NEST_VISIBLE, RDP_T_COMMENT_VISIBLE, RDP_T_NEW_ID, RDP_T_NUMBER, RDP_T_STRING,
				RDP_T_STRING_ESC, RDP_T_91, RDP_T_9142, RDP_T_93, RDP_T_94, RDP_T_9494, RDP_T_949494, RDP_T_9495,
				RDP_T_123, RDP_T_124, RDP_T_125);
		unit_first.assignList(SCAN_P_ID, RDP_T_ANNOTATED_EPSILON_TREE, RDP_T_ARG_BLANK, RDP_T_ARG_BOOLEAN,
				RDP_T_ARG_NUMERIC, RDP_T_ARG_STRING, RDP_T_CASE_INSENSITIVE, RDP_T_DERIVATION_TREE, RDP_T_EPSILON_TREE,
				RDP_T_GLOBAL, RDP_T_HASH_PRIME, RDP_T_HASH_SIZE, RDP_T_INCLUDE, RDP_T_INTERPRETER, RDP_T_MAX_ERRORS,
				RDP_T_MAX_WARNINGS, RDP_T_MULTIPLE_SOURCE_FILES, RDP_T_OPTION, RDP_T_OUTPUT_FILE, RDP_T_PARSER,
				RDP_T_PASSES, RDP_T_POST_PARSE, RDP_T_POST_PROCESS, RDP_T_PRE_PARSE, RDP_T_PRE_PROCESS,
				RDP_T_RETAIN_COMMENTS, RDP_T_SET_SIZE, RDP_T_SHOW_SKIPS, RDP_T_SUFFIX,
				RDP_T_SUPPRESS_BUILT_IN_ARGUMENTS, RDP_T_SYMBOL_TABLE, RDP_T_TAB_WIDTH, RDP_T_TEXT_SIZE, RDP_T_TITLE,
				RDP_T_TREE, RDP_T_USES);
		unit_stop.assignList(SCAN_P_EOF);
	}
}
