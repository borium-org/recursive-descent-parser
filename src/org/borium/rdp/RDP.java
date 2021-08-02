package org.borium.rdp;

import static org.borium.rdp.Arg.*;
import static org.borium.rdp.Arg.ArgKind.*;
import static org.borium.rdp.GraphBase.*;
import static org.borium.rdp.RdpAux.*;
import static org.borium.rdp.RdpAux.RdpParamType.*;
import static org.borium.rdp.RdpGram.*;
import static org.borium.rdp.Scan.*;
import static org.borium.rdp.Symbol.*;
import static org.borium.rdp.Text.*;
import static org.borium.rdp.Text.TextMessageType.*;

import java.text.*;
import java.util.*;

import org.borium.rdp.RdpAux.*;
import org.borium.rdp.Scan.*;

public class RDP
{
	private static class RdpTreeEdgeData extends GraphEdge
	{
		@SuppressWarnings("unused")
		int rdp_edge_kind;

		RdpTreeEdgeData(int kind)
		{
			rdp_edge_kind = kind;
		}
	}

	private static class RdpTreeNodeData extends GraphNode
	{
		ScanData data = new ScanData();

		@Override
		int getId()
		{
			return data.id;
		}
	}

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

	private static final int RDP_PASSES = 2;

	static String rdp_sourcefilename; // current source file name
	private static String[] rdp_sourcefilenames; // array of source file names

	private static Pointer<String> rdp_outputfilename = new Pointer<>("rdparser"); // output file name

	private static Pointer<Boolean> rdp_symbol_echo = new Pointer<>(false); // symbol echo flag
	static Pointer<Boolean> rdp_verbose = new Pointer<>(false); // verbosity flag

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

	static SymbolTable locals = null;
	static SymbolTable codes = null;
	static SymbolTable tokens = null;
	static SymbolTable rdp = null;

	private static int rdp_pass;

	/** Tree update function flag for noterminal nodes */
	private static boolean rdp_tree_update;

	private static RdpTreeNodeData rdp_tree_last_child;

	public static void main(String[] args)
	{
		long rdp_start_time = System.currentTimeMillis();

		Pointer<Boolean> rdp_symbol_statistics = new Pointer<>(false); // show symbol_ table statistics flag
		boolean rdp_line_echo_all = false; // make a listing on all passes flag
		Pointer<Boolean> rdp_filter = new Pointer<>(false); // filter flag
		Pointer<Boolean> rdp_line_echo = new Pointer<>(false); // make listing flag

		Pointer<Boolean> rdp_lexicalise = new Pointer<>(false); // print lexicalised output flag

		Pointer<Integer> rdp_textsize = new Pointer<>(600000); // size of scanner text array

		Pointer<Integer> rdp_tabwidth = new Pointer<>(8); // tab expansion width

		Pointer<String> rdp_vcg_filename = new Pointer<>(null); // filename for -V option

		Graph<RdpTreeNodeData, RdpTreeEdgeData> rdp_tree = new Graph<>();
		rdp_tree.insertGraph("RDP derivation tree");
		RdpTreeNodeData rdp_tree_root = null;

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
		if (rdp_verbose.value())
			text_printf("\nRecursive descent parser generator V1.65 (c) Adrian Johnstone 2000\n" + RDP_STAMP + "\n\n");
		for (rdp_pass = 1; rdp_pass <= RDP_PASSES; rdp_pass++)
		{
			rdp_tree_update = rdp_pass == RDP_PASSES;
			text_echo(rdp_line_echo_all || rdp_line_echo.value() && rdp_pass == RDP_PASSES);

			for (rdp_sourcefilenumber = 0; rdp_sourcefilenumber < rdp_sourcefilenames.length; rdp_sourcefilenumber++)
			{
				rdp_sourcefilename = rdp_sourcefilenames[rdp_sourcefilenumber];
				if (text_open(rdp_sourcefilename) == null)
					arg_help("unable to open source file");

				text_get_char();
				scan_();

				// call parser at top level
				unit(rdp_tree_root = rdp_add_node("unit", rdp_tree));
				if (text_total_errors() != 0)
				{
					text_message(TEXT_FATAL, "error" + (text_total_errors() == 1 ? "" : "s")
							+ " detected in source file " + rdp_sourcefilename + "\n"); // crash quietly
				}
				graph_epsilon_prune_rdp_tree(rdp_tree_root);
			}
		}

		rdp_sourcefilename = rdp_sourcefilenames[0]; // Reset filename to first file in the list

		rdp_tree.setRoot(rdp_tree_root);
		if (rdp_vcg_filename.value() != null)
		{
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
			throw new RuntimeException();
		}

		rdp_post_parse(rdp_outputfilename.value(), rdp_force.value());
		// if (rdp_symbol_statistics)
		// {
		// symbol_print_all_table_statistics(11);
		// symbol_print_all_table();
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

	protected static void unit(RdpTreeNodeData rdp_tree)
	{
		if (scan_test_set(null, rdp_unit_2_first, null))
		{
			while (true)
			{
				if (scan_test(null, SCAN_P_ID, null))
				{
					rule(rdp_add_child("rule", rdp_tree));
				}
				else if (scan_test_set(null, rdp_unit_1_first, null))
				{
					dir(rdp_add_child("dir", rdp_tree));
				}
				else
				{
					scan_test_set(null, rdp_unit_2_first, unit_stop);
				}
				if (!scan_test_set(null, rdp_unit_2_first, null))
				{
					break;
				}
			}
		}
		else if (rdp_tree_update)
		{
			RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
			temp.data.id = 0;
			temp.data.token = SCAN_P_ID;
		}
		if (rdp_rule_count == 0)
		{
			text_message(TEXT_FATAL, "no rule definitions found\n");
		}
		scan_test_set(null, unit_stop, unit_stop);
	}

	private static String code(RdpTreeNodeData rdp_tree)
	{
		String result;
		{
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_9142 /* [* */, code_stop);
			result = text_get_string(text_scan_data.id);
			scan_();
			scan_test_set(null, code_stop, code_stop);
		}
		return result;
	}

	private static void dir(RdpTreeNodeData rdp_tree)
	{
		String filename;
		String key;
		String var = null;
		String desc;
		String name;
		int size;
		int prime;
		String compare;
		String hash;
		String print;
		String data_fields;
		String s;
		int n;
		String str;
		{
			if (scan_test(null, RDP_T_INCLUDE, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_INCLUDE, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				filename = String(rdp_add_child("String", rdp_tree));
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
				if (rdp_pass == 2)
				{
					if (text_open(filename) == null)
					{
						text_message(TEXT_ERROR_ECHO, "include file '" + filename + "' not found\n");
					}
					else
					{
						text_get_char();
						scan_();
					}

				}
			}
			else if (scan_test(null, RDP_T_ARG_BOOLEAN, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_ARG_BOOLEAN, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_ID, dir_stop);
				key = text_get_string(text_scan_data.id);
				scan_();
				{ /* Start of rdp_dir_3 */
					while (true)
					{
						scan_test_set(null, rdp_dir_3_first, dir_stop);
						{
							if (scan_test(null, SCAN_P_ID, null))
							{
								if (rdp_tree_update)
								{
									rdp_add_child(null, rdp_tree);
								}
								scan_test(null, SCAN_P_ID, dir_stop);
								var = text_get_string(text_scan_data.id);
								scan_();
							}
							else if (scan_test(null, RDP_T_9142 /* [* */, null))
							{
								var = code(rdp_add_child("code", rdp_tree));
							}
							else
							{
								scan_test_set(null, rdp_dir_3_first, dir_stop);
							}
						}
						break; /* hi limit is 1! */
					}
				} /* end of rdp_dir_3 */
				desc = String(rdp_add_child("String", rdp_tree));
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
				if (rdp_pass == 2)
				{
					rdp_add_arg(ARG_BOOLEAN, key, var, desc);
				}
			}
			else if (scan_test(null, RDP_T_ARG_NUMERIC, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_ARG_NUMERIC, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_ID, dir_stop);
				key = text_get_string(text_scan_data.id);
				scan_();
				{ /* Start of rdp_dir_7 */
					while (true)
					{
						scan_test_set(null, rdp_dir_7_first, dir_stop);
						{
							if (scan_test(null, SCAN_P_ID, null))
							{
								if (rdp_tree_update)
								{
									rdp_add_child(null, rdp_tree);
								}
								scan_test(null, SCAN_P_ID, dir_stop);
								var = text_get_string(text_scan_data.id);
								scan_();
							}
							else if (scan_test(null, RDP_T_9142 /* [* */, null))
							{
								var = code(rdp_add_child("code", rdp_tree));
							}
							else
							{
								scan_test_set(null, rdp_dir_7_first, dir_stop);
							}
						}
						break; /* hi limit is 1! */
					}
				} /* end of rdp_dir_7 */
				desc = String(rdp_add_child("String", rdp_tree));
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
				if (rdp_pass == 2)
				{
					rdp_add_arg(ARG_NUMERIC, key, var, desc);
				}
			}
			else if (scan_test(null, RDP_T_ARG_STRING, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_ARG_STRING, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_ID, dir_stop);
				key = text_get_string(text_scan_data.id);
				scan_();
				{ /* Start of rdp_dir_11 */
					while (true)
					{
						scan_test_set(null, rdp_dir_11_first, dir_stop);
						{
							if (scan_test(null, SCAN_P_ID, null))
							{
								if (rdp_tree_update)
								{
									rdp_add_child(null, rdp_tree);
								}
								scan_test(null, SCAN_P_ID, dir_stop);
								var = text_get_string(text_scan_data.id);
								scan_();
							}
							else if (scan_test(null, RDP_T_9142 /* [* */, null))
							{
								var = code(rdp_add_child("code", rdp_tree));
							}
							else
							{
								scan_test_set(null, rdp_dir_11_first, dir_stop);
							}
						}
						break; /* hi limit is 1! */
					}
				} /* end of rdp_dir_11 */
				desc = String(rdp_add_child("String", rdp_tree));
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
				if (rdp_pass == 2)
				{
					rdp_add_arg(ARG_STRING, key, var, desc);
				}
			}
			else if (scan_test(null, RDP_T_ARG_BLANK, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_ARG_BLANK, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				desc = String(rdp_add_child("String", rdp_tree));
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
				if (rdp_pass == 2)
				{
					rdp_add_arg(ARG_BLANK, null, null, desc);
				}
			}
			else if (scan_test(null, RDP_T_SYMBOL_TABLE, null))
			{
				RdpTableList temp;
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_SYMBOL_TABLE, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_ID, dir_stop);
				name = text_get_string(text_scan_data.id);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_INTEGER, dir_stop);
				size = text_scan_data.i;
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_INTEGER, dir_stop);
				prime = text_scan_data.i;
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_ID, dir_stop);
				compare = text_get_string(text_scan_data.id);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_ID, dir_stop);
				hash = text_get_string(text_scan_data.id);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_ID, dir_stop);
				print = text_get_string(text_scan_data.id);
				scan_();
				data_fields = code(rdp_add_child("code", rdp_tree));
				if (rdp_pass == 2)
				{
					temp = new RdpTableList();
					temp.name = name;
					temp.size = size;
					temp.prime = prime;
					temp.compare = compare;
					temp.hash = hash;
					temp.print = print;
					temp.data_fields = data_fields;
					temp.next = rdp_dir_symbol_table;
					rdp_dir_symbol_table = temp;
				}
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_USES, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_USES, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				s = String(rdp_add_child("String", rdp_tree));
				if (rdp_pass == 2)
				{
					RdpStringList temp = new RdpStringList();
					temp.str1 = s;
					temp.next = rdp_dir_include;
					rdp_dir_include = temp;

				}
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_OUTPUT_FILE, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_OUTPUT_FILE, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				s = String(rdp_add_child("String", rdp_tree));
				rdp_dir_output_file = s;
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_PARSER, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_PARSER, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_ID, dir_stop);
				name = text_get_string(text_scan_data.id);
				scan_();
				if (rdp_pass == 2)
				{
					rdp_start_prod = rdp_find(name, K_PRIMARY, RDP_OLD);
					rdp_start_prod.call_count++;
				}
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_POST_PARSE, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_POST_PARSE, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				s = code(rdp_add_child("code", rdp_tree));
				rdp_dir_post_parse = s;
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_PRE_PARSE, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_PRE_PARSE, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				s = code(rdp_add_child("code", rdp_tree));
				rdp_dir_pre_parse = s;
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_GLOBAL, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_GLOBAL, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				s = code(rdp_add_child("code", rdp_tree));
				rdp_dir_global = s;
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_SUFFIX, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_SUFFIX, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				s = String(rdp_add_child("String", rdp_tree));
				rdp_dir_suffix = s;
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_TITLE, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_TITLE, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				s = String(rdp_add_child("String", rdp_tree));
				rdp_dir_title = s;
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_MAX_ERRORS, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_MAX_ERRORS, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_INTEGER, dir_stop);
				n = text_scan_data.i;
				scan_();
				rdp_dir_max_errors = n;
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_MAX_WARNINGS, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_MAX_WARNINGS, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_INTEGER, dir_stop);
				n = text_scan_data.i;
				scan_();
				rdp_dir_max_warnings = n;
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_PASSES, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_PASSES, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_INTEGER, dir_stop);
				n = text_scan_data.i;
				scan_();
				rdp_dir_passes = n;
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_MULTIPLE_SOURCE_FILES, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_MULTIPLE_SOURCE_FILES, dir_stop);
				scan_();
				rdp_dir_multiple_source_files = 1;
			}
			else if (scan_test(null, RDP_T_TAB_WIDTH, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_TAB_WIDTH, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_INTEGER, dir_stop);
				n = text_scan_data.i;
				scan_();
				rdp_dir_tab_width = n;
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_TEXT_SIZE, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_TEXT_SIZE, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_INTEGER, dir_stop);
				n = text_scan_data.i;
				scan_();
				rdp_dir_text_size = n;
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test_set(null, rdp_dir_37_first, null))
			{
				{ /* Start of rdp_dir_34 */
					while (true)
					{
						scan_test_set(null, rdp_dir_34_first, dir_stop);
						{
							if (scan_test(null, RDP_T_DERIVATION_TREE, null))
							{
								if (rdp_tree_update)
								{
									rdp_add_child(null, rdp_tree);
								}
								scan_test(null, RDP_T_DERIVATION_TREE, dir_stop);
								scan_();
								rdp_dir_derivation_tree = 1;
							}
							else if (scan_test(null, RDP_T_ANNOTATED_EPSILON_TREE, null))
							{
								if (rdp_tree_update)
								{
									rdp_add_child(null, rdp_tree);
								}
								scan_test(null, RDP_T_ANNOTATED_EPSILON_TREE, dir_stop);
								scan_();
								rdp_dir_annotated_epsilon_tree = 1;
								rdp_dir_epsilon_tree = 1;
							}
							else if (scan_test(null, RDP_T_EPSILON_TREE, null))
							{
								if (rdp_tree_update)
								{
									rdp_add_child(null, rdp_tree);
								}
								scan_test(null, RDP_T_EPSILON_TREE, dir_stop);
								scan_();
								rdp_dir_epsilon_tree = 1;
							}
							else if (scan_test(null, RDP_T_SUPPRESS_BUILT_IN_ARGUMENTS, null))
							{
								if (rdp_tree_update)
								{
									rdp_add_child(null, rdp_tree);
								}
								scan_test(null, RDP_T_SUPPRESS_BUILT_IN_ARGUMENTS, dir_stop);
								scan_();
								rdp_dir_args = null;
							}
							else if (scan_test(null, RDP_T_TREE, null))
							{
								if (rdp_tree_update)
								{
									rdp_add_child(null, rdp_tree);
								}
								scan_test(null, RDP_T_TREE, dir_stop);
								scan_();
							}
							else
							{
								scan_test_set(null, rdp_dir_34_first, dir_stop);
							}
						}
						break; /* hi limit is 1! */
					}
				} /* end of rdp_dir_34 */
				if (rdp_pass == 1)
				{
					rdp_dir_tree = 1;
				}
				if (scan_test(null, RDP_T_40 /* ( */, null))
				{ /* Start of rdp_dir_36 */
					while (true)
					{
						{
							if (rdp_tree_update)
							{
								rdp_add_child(null, rdp_tree);
							}
							scan_test(null, RDP_T_40 /* ( */, dir_stop);
							scan_();
							str = code(rdp_add_child("code", rdp_tree));
							rdp_dir_tree_node_fields = str;
							str = code(rdp_add_child("code", rdp_tree));
							rdp_dir_tree_edge_fields = str;
							if (rdp_tree_update)
							{
								rdp_add_child(null, rdp_tree);
							}
							scan_test(null, RDP_T_41 /* ) */, dir_stop);
							scan_();
						}
						break; /* hi limit is 1! */
					}
				} /* end of rdp_dir_36 */
				else
				{
					/* default action processing for rdp_dir_36 */
					if (rdp_tree_update)
					{
						RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
						temp.data.id = 0;
						temp.data.token = SCAN_P_ID;
					}
				}
			}
			else if (scan_test(null, RDP_T_CASE_INSENSITIVE, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_CASE_INSENSITIVE, dir_stop);
				scan_();
				rdp_dir_case_insensitive = 1;
			}
			else if (scan_test(null, RDP_T_RETAIN_COMMENTS, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_RETAIN_COMMENTS, dir_stop);
				scan_();
				rdp_dir_retain_comments = 1;
			}
			else if (scan_test(null, RDP_T_SHOW_SKIPS, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_SHOW_SKIPS, dir_stop);
				scan_();
				rdp_dir_show_skips = 1;
			}
			else if (scan_test(null, RDP_T_OPTION, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_OPTION, dir_stop);
				scan_();
				text_message(TEXT_ERROR_ECHO, "Obsolete directive: OPTION replaced by ARG_* at version 1.5\n");
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				String(rdp_add_child("String", rdp_tree));
				if (scan_test(null, RDP_T_9142 /* [* */, null))
				{ /* Start of rdp_dir_42 */
					while (true)
					{
						{
							code(rdp_add_child("code", rdp_tree));
						}
						break; /* hi limit is 1! */
					}
				} /* end of rdp_dir_42 */
				else
				{
					/* default action processing for rdp_dir_42 */
					if (rdp_tree_update)
					{
						RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
						temp.data.id = 0;
						temp.data.token = SCAN_P_ID;
					}
				}
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_PRE_PROCESS, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_PRE_PROCESS, dir_stop);
				scan_();
				text_message(TEXT_ERROR_ECHO, "Obsolete directive: PRE_PROCESS renamed PRE_PARSE at version 1.3\n");
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				code(rdp_add_child("code", rdp_tree));
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_POST_PROCESS, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_POST_PROCESS, dir_stop);
				scan_();
				text_message(TEXT_ERROR_ECHO, "Obsolete directive: POST_PROCESS renamed POST_PARSE at version 1.3\n");
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				code(rdp_add_child("code", rdp_tree));
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_SET_SIZE, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_SET_SIZE, dir_stop);
				scan_();
				text_message(TEXT_ERROR_ECHO, "Obsolete directive: SET_SIZE deleted at version 1.4\n");
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_INTEGER, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_HASH_SIZE, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_HASH_SIZE, dir_stop);
				scan_();
				text_message(TEXT_ERROR_ECHO,
						"Obsolete directive: HASH_SIZE replaced by SYMBOL_TABLE at version 1.4\n");
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_INTEGER, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_HASH_PRIME, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_HASH_PRIME, dir_stop);
				scan_();
				text_message(TEXT_ERROR_ECHO,
						"Obsolete directive: HASH_PRIME replaced by SYMBOL_TABLE at version 1.4\n");
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_INTEGER, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else if (scan_test(null, RDP_T_INTERPRETER, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_INTERPRETER, dir_stop);
				scan_();
				text_message(TEXT_ERROR_ECHO, "Obsolete directive: INTERPRETER mode deleted at version 1.4\n");
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, SCAN_P_INTEGER, dir_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, dir_stop);
				scan_();
			}
			else
			{
				scan_test_set(null, dir_first, dir_stop);
			}
			scan_test_set(null, dir_stop, dir_stop);
		}
	}

	private static RdpData item_com(RdpTreeNodeData rdp_tree)
	{
		RdpData result = null;
		String name;
		String close;
		{
			if (scan_test(null, RDP_T_COMMENT, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_COMMENT, item_com_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, item_com_stop);
				scan_();
				name = token(rdp_add_child("token", rdp_tree));
				close = token(rdp_add_child("token", rdp_tree));
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, item_com_stop);
				scan_();
				if (rdp_pass == 2)
				{
					result = rdp_find_extended(name, close, SCAN_P_COMMENT);
				}
			}
			else if (scan_test(null, RDP_T_COMMENT_LINE, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_COMMENT_LINE, item_com_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, item_com_stop);
				scan_();
				name = token(rdp_add_child("token", rdp_tree));
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, item_com_stop);
				scan_();
				if (rdp_pass == 2)
				{
					result = rdp_find_extended(name, null, SCAN_P_COMMENT_LINE);
				}
			}
			else if (scan_test(null, RDP_T_COMMENT_NEST, null))
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_COMMENT_NEST, item_com_stop);
				scan_();
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, item_com_stop);
				scan_();
				name = token(rdp_add_child("token", rdp_tree));
				close = token(rdp_add_child("token", rdp_tree));
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, item_com_stop);
				scan_();
				if (rdp_pass == 2)
				{
					result = rdp_find_extended(name, close, SCAN_P_COMMENT_NEST);
				}
			}
			else
			{
				scan_test_set(null, item_com_first, item_com_stop);
			}
			scan_test_set(null, item_com_stop, item_com_stop);
		}
		return result;
	}

	private static RdpData item_inl(RdpTreeNodeData rdp_tree)
	{
		RdpData result = null;
		String name;
		int pass;
		RdpList body;
		int val;
		String delimiter;
		{
			int promote_op = PROMOTE_DEFAULT;
			{ /* Start of rdp_item_inl_28 */
				while (true)
				{
					scan_test_set(null, rdp_item_inl_28_first, item_inl_stop);
					{
						if (scan_test(null, RDP_T_9142 /* [* */, null))
						{
							name = code(rdp_add_child("code", rdp_tree));
							if (rdp_pass == 2)
							{
								result = rdp_find(name, K_CODE, RDP_ANY);
								result.contains_null = true;
								result.call_count++; /* increment call count */

							}
							if (scan_test(null, RDP_T_64 /* @ */, null))
							{ /* Start of rdp_item_inl_1 */
								while (true)
								{
									{
										if (rdp_tree_update)
										{
											rdp_add_child(null, rdp_tree);
										}
										scan_test(null, RDP_T_64 /* @ */, item_inl_stop);
										scan_();
										if (rdp_tree_update)
										{
											rdp_add_child(null, rdp_tree);
										}
										scan_test(null, SCAN_P_INTEGER, item_inl_stop);
										pass = text_scan_data.i;
										scan_();
										if (rdp_pass == 2)
										{
											result.code_pass = pass;
										}
									}
									break; /* hi limit is 1! */
								}
							} /* end of rdp_item_inl_1 */
							else
							{
								/* default action processing for rdp_item_inl_1 */
								if (rdp_tree_update)
								{
									RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
									temp.data.id = 0;
									temp.data.token = SCAN_P_ID;
								}
							}
						}
						else if (scan_test(null, RDP_T_40 /* ( */, null))
						{
							if (rdp_tree_update)
							{
								rdp_add_child(null, rdp_tree);
							}
							scan_test(null, RDP_T_40 /* ( */, item_inl_stop);
							scan_();
							body = prod(rdp_add_child("prod", rdp_tree));
							if (rdp_tree_update)
							{
								rdp_add_child(null, rdp_tree);
							}
							scan_test(null, RDP_T_41 /* ) */, item_inl_stop);
							scan_();
							if (rdp_pass == 2)
							{
								result = rdp_find(text_insert_substring("rdp", rdp_primary_id, rdp_component++), K_LIST,
										RDP_ANY);
								result.promote = promote_op;
								result.list = body;
								result.call_count++;
								result.lo = 1;
								result.hi = 1;

							}
							if (scan_test_set(null, rdp_item_inl_9_first, null))
							{ /* Start of rdp_item_inl_9 */
								while (true)
								{
									{
										{ /* Start of rdp_item_inl_7 */
											while (true)
											{
												scan_test_set(null, rdp_item_inl_7_first, item_inl_stop);
												{
													if (scan_test(null, RDP_T_9495 /* ^ _ */, null))
													{
														if (rdp_tree_update)
														{
															rdp_add_child(null, rdp_tree);
														}
														scan_test(null, RDP_T_9495 /* ^ _ */, item_inl_stop);
														scan_();
													}
													else if (scan_test(null, RDP_T_94 /* ^ */, null))
													{
														if (rdp_tree_update)
														{
															rdp_add_child(null, rdp_tree);
														}
														scan_test(null, RDP_T_94 /* ^ */, item_inl_stop);
														scan_();
													}
													else if (scan_test(null, RDP_T_9494 /* ^ ^ */, null))
													{
														if (rdp_tree_update)
														{
															rdp_add_child(null, rdp_tree);
														}
														scan_test(null, RDP_T_9494 /* ^ ^ */, item_inl_stop);
														scan_();
													}
													else if (scan_test(null, RDP_T_949494 /* ^ ^ ^ */, null))
													{
														if (rdp_tree_update)
														{
															rdp_add_child(null, rdp_tree);
														}
														scan_test(null, RDP_T_949494 /* ^ ^ ^ */, item_inl_stop);
														scan_();
													}
													else
													{
														scan_test_set(null, rdp_item_inl_7_first, item_inl_stop);
													}
												}
												break; /* hi limit is 1! */
											}
										} /* end of rdp_item_inl_7 */
										if (rdp_pass == 2)
										{
											text_message(TEXT_ERROR_ECHO,
													"Promotion operators may not be attached to do-first expressions\n");
										}
									}
									break; /* hi limit is 1! */
								}
							} /* end of rdp_item_inl_9 */
							else
							{
								/* default action processing for rdp_item_inl_9 */
								if (rdp_tree_update)
								{
									RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
									temp.data.id = 0;
									temp.data.token = SCAN_P_ID;
								}
							}
							if (scan_test_set(null, rdp_item_inl_23_first, null))
							{ /* Start of rdp_item_inl_23 */
								while (true)
								{
									{
										if (scan_test(null, SCAN_P_INTEGER, null))
										{ /* Start of rdp_item_inl_11 */
											while (true)
											{
												{
													if (rdp_tree_update)
													{
														rdp_add_child(null, rdp_tree);
													}
													scan_test(null, SCAN_P_INTEGER, item_inl_stop);
													val = text_scan_data.i;
													scan_();
													if (rdp_pass == 2)
													{
														result.lo = val;
													}
												}
												break; /* hi limit is 1! */
											}
										} /* end of rdp_item_inl_11 */
										else
										{
											/* default action processing for rdp_item_inl_11 */
											if (rdp_tree_update)
											{
												RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
												temp.data.id = 0;
												temp.data.token = SCAN_P_ID;
											}
										}
										if (rdp_tree_update)
										{
											rdp_add_child(null, rdp_tree);
										}
										scan_test(null, RDP_T_64 /* @ */, item_inl_stop);
										scan_();
										if (rdp_pass == 2)
										{
											result.hi = 0; /* iterate for ever by default */
										}
										if (scan_test(null, SCAN_P_INTEGER, null))
										{ /* Start of rdp_item_inl_13 */
											while (true)
											{
												{
													if (rdp_tree_update)
													{
														rdp_add_child(null, rdp_tree);
													}
													scan_test(null, SCAN_P_INTEGER, item_inl_stop);
													val = text_scan_data.i;
													scan_();
													if (rdp_pass == 2)
													{
														result.hi = val;
													}
												}
												break; /* hi limit is 1! */
											}
										} /* end of rdp_item_inl_13 */
										else
										{
											/* default action processing for rdp_item_inl_13 */
											if (rdp_tree_update)
											{
												RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
												temp.data.id = 0;
												temp.data.token = SCAN_P_ID;
											}
										}
										if (rdp_pass == 2)
										{
											if (result.lo == 0)
											{
												result.contains_null = true;
											}
											if (result.hi != 0 && result.hi < result.lo)
											{
												text_message(TEXT_ERROR_ECHO,
														"Iterator high count must be greater than low count\n");
											}

										}
										if (rdp_pass == 2)
										{
											result.delimiter_promote = PROMOTE_DONT;
										}
										{ /* Start of rdp_item_inl_16 */
											while (true)
											{
												scan_test_set(null, rdp_item_inl_16_first, item_inl_stop);
												{
													if (scan_test(null, RDP_T_39 /* ' */, null))
													{
														delimiter = token(rdp_add_child("token", rdp_tree));
														if (rdp_pass == 2)
														{
															result.supplementary_token = rdp_find(delimiter, K_TOKEN,
																	RDP_ANY);
														}
													}
													else if (scan_test(null, RDP_T_35 /* # */, null))
													{
														if (rdp_tree_update)
														{
															rdp_add_child(null, rdp_tree);
														}
														scan_test(null, RDP_T_35 /* # */, item_inl_stop);
														scan_();
													}
													else
													{
														scan_test_set(null, rdp_item_inl_16_first, item_inl_stop);
													}
												}
												break; /* hi limit is 1! */
											}
										} /* end of rdp_item_inl_16 */
										if (scan_test_set(null, rdp_item_inl_21_first, null))
										{ /* Start of rdp_item_inl_21 */
											while (true)
											{
												{
													if (scan_test(null, RDP_T_9495 /* ^ _ */, null))
													{
														if (rdp_tree_update)
														{
															rdp_add_child(null, rdp_tree);
														}
														scan_test(null, RDP_T_9495 /* ^ _ */, item_inl_stop);
														scan_();
														if (rdp_pass == 2)
														{
															result.delimiter_promote = PROMOTE_DONT;
														}
													}
													else if (scan_test(null, RDP_T_94 /* ^ */, null))
													{
														if (rdp_tree_update)
														{
															rdp_add_child(null, rdp_tree);
														}
														scan_test(null, RDP_T_94 /* ^ */, item_inl_stop);
														scan_();
														if (rdp_pass == 2)
														{
															result.delimiter_promote = PROMOTE;
														}
													}
													else if (scan_test(null, RDP_T_9494 /* ^ ^ */, null))
													{
														if (rdp_tree_update)
														{
															rdp_add_child(null, rdp_tree);
														}
														scan_test(null, RDP_T_9494 /* ^ ^ */, item_inl_stop);
														scan_();
														if (rdp_pass == 2)
														{
															result.delimiter_promote = PROMOTE_AND_COPY;
														}
													}
													else if (scan_test(null, RDP_T_949494 /* ^ ^ ^ */, null))
													{
														if (rdp_tree_update)
														{
															rdp_add_child(null, rdp_tree);
														}
														scan_test(null, RDP_T_949494 /* ^ ^ ^ */, item_inl_stop);
														scan_();
														if (rdp_pass == 2)
														{
															result.delimiter_promote = PROMOTE_ABOVE;
														}
													}
													else
													{
														scan_test_set(null, rdp_item_inl_21_first, item_inl_stop);
													}
												}
												break; /* hi limit is 1! */
											}
										} /* end of rdp_item_inl_21 */
										else
										{
											/* default action processing for rdp_item_inl_21 */
											if (rdp_tree_update)
											{
												RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
												temp.data.id = 0;
												temp.data.token = SCAN_P_ID;
											}
										}
									}
									break; /* hi limit is 1! */
								}
							} /* end of rdp_item_inl_23 */
							else
							{
								/* default action processing for rdp_item_inl_23 */
								if (rdp_tree_update)
								{
									RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
									temp.data.id = 0;
									temp.data.token = SCAN_P_ID;
								}
							}
						}
						else if (scan_test(null, RDP_T_123 /* { */, null))
						{
							if (rdp_tree_update)
							{
								rdp_add_child(null, rdp_tree);
							}
							scan_test(null, RDP_T_123 /* { */, item_inl_stop);
							scan_();
							body = prod(rdp_add_child("prod", rdp_tree));
							if (rdp_tree_update)
							{
								rdp_add_child(null, rdp_tree);
							}
							scan_test(null, RDP_T_125 /* } */, item_inl_stop);
							scan_();
							if (rdp_pass == 2)
							{
								result = rdp_find(text_insert_substring("rdp", rdp_primary_id, rdp_component++), K_LIST,
										RDP_ANY);
								result.promote = promote_op;
								result.list = body;
								result.contains_null = true;
								result.call_count++;
								result.lo = 0;
								result.hi = 0;

							}
						}
						else if (scan_test(null, RDP_T_91 /* [ */, null))
						{
							if (rdp_tree_update)
							{
								rdp_add_child(null, rdp_tree);
							}
							scan_test(null, RDP_T_91 /* [ */, item_inl_stop);
							scan_();
							body = prod(rdp_add_child("prod", rdp_tree));
							if (rdp_tree_update)
							{
								rdp_add_child(null, rdp_tree);
							}
							scan_test(null, RDP_T_93 /* ] */, item_inl_stop);
							scan_();
							if (rdp_pass == 2)
							{
								result = rdp_find(text_insert_substring("rdp", rdp_primary_id, rdp_component++), K_LIST,
										RDP_ANY);
								result.promote = promote_op;
								result.list = body;
								result.contains_null = true;
								result.call_count++;
								result.lo = 0;
								result.hi = 1;

							}
						}
						else if (scan_test(null, RDP_T_60 /* < */, null))
						{
							if (rdp_tree_update)
							{
								rdp_add_child(null, rdp_tree);
							}
							scan_test(null, RDP_T_60 /* < */, item_inl_stop);
							scan_();
							body = prod(rdp_add_child("prod", rdp_tree));
							if (rdp_tree_update)
							{
								rdp_add_child(null, rdp_tree);
							}
							scan_test(null, RDP_T_62 /* > */, item_inl_stop);
							scan_();
							if (rdp_pass == 2)
							{
								result = rdp_find(text_insert_substring("rdp", rdp_primary_id, rdp_component++), K_LIST,
										RDP_ANY);
								result.promote = promote_op;
								result.list = body;
								result.call_count++;
								result.lo = 1;
								result.hi = 0;

							}
						}
						else
						{
							scan_test_set(null, rdp_item_inl_28_first, item_inl_stop);
						}
					}
					break; /* hi limit is 1! */
				}
			} /* end of rdp_item_inl_28 */
			scan_test_set(null, item_inl_stop, item_inl_stop);
		}
		return result;
	}

	private static RdpData item_ret(RdpTreeNodeData rdp_tree)
	{
		RdpData result = null;
		String name;
		int n;
		double r;
		String str;
		String quote;
		String close;
		if (scan_test(null, SCAN_P_ID, null))
		{
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, SCAN_P_ID, item_ret_stop);
			name = text_get_string(text_scan_data.id);
			scan_();
			if (rdp_pass == 2)
			{
				result = rdp_find(name, K_PRIMARY, RDP_OLD);
				rdp_check_eoln(name);
				result.call_count++; /* increment call count */
				result.actuals = null;

			}
			if (rdp_pass == 2 && result.parameterised)
			{
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_40 /* ( */, item_ret_stop);
				scan_();
				if (scan_test_set(null, rdp_item_ret_6_first, null))
				{ /* Start of rdp_item_ret_6 */
					while (true)
					{
						RdpParamList param;

						/* Start of rdp_item_ret_4 */
						while (true)
						{
							scan_test_set(null, rdp_item_ret_4_first, item_ret_stop);
							if (scan_test(null, SCAN_P_INTEGER, null))
							{
								if (rdp_tree_update)
								{
									rdp_add_child(null, rdp_tree);
								}
								scan_test(null, SCAN_P_INTEGER, item_ret_stop);
								n = text_scan_data.i;
								scan_();
								if (rdp_pass == 2)
								{
									param = new RdpParamList();
									param.n = n;
									param.next = result.actuals;
									result.actuals = param;
									param.flavour = PARAM_INTEGER;

								}
							}
							else if (scan_test(null, SCAN_P_REAL, null))
							{
								if (rdp_tree_update)
								{
									rdp_add_child(null, rdp_tree);
								}
								scan_test(null, SCAN_P_REAL, item_ret_stop);
								r = text_scan_data.r;
								scan_();
								if (rdp_pass == 2)
								{
									param = new RdpParamList();
									param.r = r;
									param.next = result.actuals;
									result.actuals = param;
									param.flavour = PARAM_REAL;

								}
							}
							else if (scan_test(null, RDP_T_34 /* " */, null))
							{
								str = String(rdp_add_child("String", rdp_tree));
								if (rdp_pass == 2)
								{
									param = new RdpParamList();
									param.id = str;
									param.next = result.actuals;
									result.actuals = param;
									param.flavour = PARAM_STRING;
								}
							}
							else if (scan_test(null, SCAN_P_ID, null))
							{
								if (rdp_tree_update)
								{
									rdp_add_child(null, rdp_tree);
								}
								scan_test(null, SCAN_P_ID, item_ret_stop);
								name = text_get_string(text_scan_data.id);
								scan_();
								if (rdp_pass == 2)
								{
									param = new RdpParamList();
									param.id = name;
									param.next = result.actuals;
									result.actuals = param;
									param.flavour = PARAM_ID;

								}
							}
							else
							{
								scan_test_set(null, rdp_item_ret_4_first, item_ret_stop);
							}
							break; /* hi limit is 1! */
						}
						if (!scan_test_set(null, rdp_item_ret_6_first, null))
						{
							break;
						}
					}
				} /* end of rdp_item_ret_6 */
				else
				{
					/* default action processing for rdp_item_ret_6 */
					if (rdp_tree_update)
					{
						// rdp_tree_node_data temp = rdp_add_child(null,
						// rdp_tree);
						// temp.id = null;
						// temp.token = SCAN_P_ID;
					}
				}
				if (rdp_tree_update)
				{
					rdp_add_child(null, rdp_tree);
				}
				scan_test(null, RDP_T_41 /* ) */, item_ret_stop);
				scan_();
			}
		}
		else if (scan_test(null, RDP_T_39 /* ' */, null))
		{
			name = token(rdp_add_child("token", rdp_tree));
			if (rdp_pass == 2)
			{
				result = rdp_process_token(name);
			}
		}
		else if (scan_test(null, RDP_T_CHAR, null))
		{
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_CHAR, item_ret_stop);
			scan_();
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_40 /* ( */, item_ret_stop);
			scan_();
			name = token(rdp_add_child("token", rdp_tree));
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_41 /* ) */, item_ret_stop);
			scan_();
			if (rdp_pass == 2)
			{
				result = rdp_find_extended(name, null, SCAN_P_CHAR);
			}
		}
		else if (scan_test(null, RDP_T_CHAR_ESC, null))
		{
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_CHAR_ESC, item_ret_stop);
			scan_();
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_40 /* ( */, item_ret_stop);
			scan_();
			name = token(rdp_add_child("token", rdp_tree));
			quote = token(rdp_add_child("token", rdp_tree));
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_41 /* ) */, item_ret_stop);
			scan_();
			if (rdp_pass == 2)
			{
				result = rdp_find_extended(name, quote, SCAN_P_CHAR_ESC);
			}
		}
		else if (scan_test(null, RDP_T_STRING, null))
		{
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_STRING, item_ret_stop);
			scan_();
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_40 /* ( */, item_ret_stop);
			scan_();
			name = token(rdp_add_child("token", rdp_tree));
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_41 /* ) */, item_ret_stop);
			scan_();
			if (rdp_pass == 2)
			{
				result = rdp_find_extended(name, null, SCAN_P_STRING);
			}
		}
		else if (scan_test(null, RDP_T_STRING_ESC, null))
		{
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_STRING_ESC, item_ret_stop);
			scan_();
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_40 /* ( */, item_ret_stop);
			scan_();
			name = token(rdp_add_child("token", rdp_tree));
			quote = token(rdp_add_child("token", rdp_tree));
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_41 /* ) */, item_ret_stop);
			scan_();
			if (rdp_pass == 2)
			{
				result = rdp_find_extended(name, quote, SCAN_P_STRING_ESC);
			}
		}
		else if (scan_test(null, RDP_T_COMMENT_VISIBLE, null))
		{
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_COMMENT_VISIBLE, item_ret_stop);
			scan_();
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_40 /* ( */, item_ret_stop);
			scan_();
			name = token(rdp_add_child("token", rdp_tree));
			close = token(rdp_add_child("token", rdp_tree));
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_41 /* ) */, item_ret_stop);
			scan_();
			if (rdp_pass == 2)
			{
				result = rdp_find_extended(name, close, SCAN_P_COMMENT_VISIBLE);
			}
		}
		else if (scan_test(null, RDP_T_COMMENT_NEST_VISIBLE, null))
		{
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_COMMENT_NEST_VISIBLE, item_ret_stop);
			scan_();
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_40 /* ( */, item_ret_stop);
			scan_();
			name = token(rdp_add_child("token", rdp_tree));
			close = token(rdp_add_child("token", rdp_tree));
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_41 /* ) */, item_ret_stop);
			scan_();
			if (rdp_pass == 2)
			{
				result = rdp_find_extended(name, close, SCAN_P_COMMENT_NEST_VISIBLE);
			}
		}
		else if (scan_test(null, RDP_T_COMMENT_LINE_VISIBLE, null))
		{
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_COMMENT_LINE_VISIBLE, item_ret_stop);
			scan_();
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_40 /* ( */, item_ret_stop);
			scan_();
			name = token(rdp_add_child("token", rdp_tree));
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_41 /* ) */, item_ret_stop);
			scan_();
			if (rdp_pass == 2)
			{
				result = rdp_find_extended(name, null, SCAN_P_COMMENT_LINE_VISIBLE);
			}
		}
		else if (scan_test(null, RDP_T_NUMBER, null))
		{
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_NUMBER, item_ret_stop);
			scan_();
			text_message(TEXT_ERROR_ECHO, "Obsolete scanner primitive: NUMBER renamed INTEGER at version 1.3\n");
		}
		else if (scan_test(null, RDP_T_NEW_ID, null))
		{
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_NEW_ID, item_ret_stop);
			scan_();
			text_message(TEXT_ERROR_ECHO, "Obsolete scanner primitive: NEW_ID deleted at version 1.4\n");
		}
		else if (scan_test(null, RDP_T_ALT_ID, null))
		{
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_ALT_ID, item_ret_stop);
			scan_();
			text_message(TEXT_ERROR_ECHO, "Obsolete scanner primitive: ALT_ID deleted at version 1.4\n");
		}
		else if (scan_test(null, RDP_T_34 /* " */, null))
		{
			str = String(rdp_add_child("String", rdp_tree));
			if (rdp_pass == 2)
			{
				text_message(TEXT_ERROR_ECHO, "Illegal grammar element: perhaps you intended to write '" + str + "'\n");
			}
		}
		else if (scan_test(null, SCAN_P_INTEGER, null))
		{
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, SCAN_P_INTEGER, item_ret_stop);
			scan_();
			if (rdp_pass == 2)
			{
				text_message(TEXT_ERROR_ECHO, "Illegal grammar element: an integer may not appear here\n");
			}
		}
		else if (scan_test(null, SCAN_P_REAL, null))
		{
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, SCAN_P_REAL, item_ret_stop);
			scan_();
			if (rdp_pass == 2)
			{
				text_message(TEXT_ERROR_ECHO, "Illegal grammar element: a real may not appear here\n");
			}
		}
		else
		{
			scan_test_set(null, item_ret_first, item_ret_stop);
		}
		scan_test_set(null, item_ret_stop, item_ret_stop);
		return result;
	}

	private static RdpList prod(RdpTreeNodeData rdp_tree)
	{
		RdpList result = null;
		RdpList body;

		RdpList end = null;
		if (rdp_pass == 2)
		{
			end = result = new RdpList();
		}
		/* Start of rdp_prod_1 */
		while (true)
		{
			scan_test_set(null, rdp_prod_1_first, prod_stop);

			body = seq(rdp_add_child("seq", rdp_tree));
			if (rdp_pass == 2)
			{
				end.next = new RdpList();
				end = end.next;
				end.production = rdp_find(text_insert_substring("rdp", rdp_primary_id, rdp_component++), K_SEQUENCE,
						RDP_NEW);
				end.production.call_count++;
				end.production.list = body;
			}
			if (text_scan_data.token != RDP_T_124 /* | */)
			{
				break;
			}
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_();
		}
		/* end of rdp_prod_1 */
		if (rdp_pass == 2)
		{
			result = result.next;
		}
		scan_test_set(null, prod_stop, prod_stop);
		return result;
	}

	private static RdpTreeNodeData rdp_add_child(String id, RdpTreeNodeData rdp_tree)
	{
		if (rdp_tree_update)
		{
			rdp_tree_last_child = new RdpTreeNodeData();
			rdp_tree.insertNode(rdp_tree_last_child);
			if (id != null)
			{
				rdp_tree_last_child.data.id = text_insert_string(id);
			}
			else
			{
				memcpy(rdp_tree_last_child.data, text_scan_data);
			}
			new RdpTreeEdgeData(1).insertEdgeAfterFinal(rdp_tree_last_child, rdp_tree);
			return rdp_tree_last_child;
		}
		return null;
	}

	private static RdpTreeNodeData rdp_add_node(String id, Graph<RdpTreeNodeData, RdpTreeEdgeData> rdp_tree)
	{
		if (rdp_tree_update)
		{
			RdpTreeNodeData node = new RdpTreeNodeData();
			rdp_tree.insertNode(node);
			if (id != null)
			{
				node.data.id = text_insert_string(id);
			}
			else
			{
				memcpy(node.data, text_scan_data);
			}
			return node;
		}
		return null;
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

	private static RdpData rule(RdpTreeNodeData rdp_tree)
	{
		RdpData result;
		int name;
		int type;
		RdpList body;
		{
			int stars;
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, SCAN_P_ID, rule_stop);
			name = text_scan_data.id;
			scan_();
			rdp_primary_id = text_get_string(name);
			rdp_component = 0;
			rdp_rule_count++;
			result = rdp_find(rdp_primary_id, K_PRIMARY, rdp_pass == 1 ? RDP_NEW : RDP_OLD);
			if (rdp_start_prod == null)
			{
				rdp_start_prod = result;
				result.call_count++;
			}

			if (scan_test(null, RDP_T_40 /* ( */, null))
			{ /* Start of rdp_rule_7 */
				while (true)
				{
					{
						if (rdp_tree_update)
						{
							rdp_add_child(null, rdp_tree);
						}
						scan_test(null, RDP_T_40 /* ( */, rule_stop);
						scan_();
						result.parameterised = true;
						if (scan_test(null, SCAN_P_ID, null))
						{ /* Start of rdp_rule_5 */
							while (true)
							{
								{
									RdpParamList param;
									type = text_insert_string("void");
									stars = 0;
									if (rdp_tree_update)
									{
										rdp_add_child(null, rdp_tree);
									}
									scan_test(null, SCAN_P_ID, rule_stop);
									name = text_scan_data.id;
									scan_();
									if (scan_test(null, RDP_T_58 /* : */, null))
									{ /* Start of rdp_rule_3 */
										while (true)
										{
											{
												if (rdp_tree_update)
												{
													rdp_add_child(null, rdp_tree);
												}
												scan_test(null, RDP_T_58 /* : */, rule_stop);
												scan_();
												if (rdp_tree_update)
												{
													rdp_add_child(null, rdp_tree);
												}
												scan_test(null, SCAN_P_ID, rule_stop);
												type = text_scan_data.id;
												scan_();
												if (scan_test(null, RDP_T_42 /* * */, null))
												{ /* Start of rdp_rule_1 */
													while (true)
													{
														{
															if (rdp_tree_update)
															{
																rdp_add_child(null, rdp_tree);
															}
															scan_test(null, RDP_T_42 /* * */, rule_stop);
															scan_();
															stars++;
														}
														if (!scan_test(null, RDP_T_42 /* * */, null))
														{
															break;
														}
													}
												} /* end of rdp_rule_1 */
												else
												{
													/* default action processing for rdp_rule_1 */
													if (rdp_tree_update)
													{
														RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
														temp.data.id = 0;
														temp.data.token = SCAN_P_ID;
													}
												}
											}
											break; /* hi limit is 1! */
										}
									} /* end of rdp_rule_3 */
									else
									{
										/* default action processing for rdp_rule_3 */
										if (rdp_tree_update)
										{
											RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
											temp.data.id = 0;
											temp.data.token = SCAN_P_ID;
										}
									}
									if (rdp_pass == 1)
									{
										param = new RdpParamList();
										param.id = text_get_string(name);
										param.type = text_get_string(type);
										param.stars = stars;
										param.next = result.params;
										result.params = param;
									}
								}
								if (!scan_test(null, SCAN_P_ID, null))
								{
									break;
								}
							}
						} /* end of rdp_rule_5 */
						else
						{
							/* default action processing for rdp_rule_5 */
							if (rdp_tree_update)
							{
								RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
								temp.data.id = 0;
								temp.data.token = SCAN_P_ID;
							}
						}
						if (rdp_tree_update)
						{
							rdp_add_child(null, rdp_tree);
						}
						scan_test(null, RDP_T_41 /* ) */, rule_stop);
						scan_();
					}
					break; /* hi limit is 1! */
				}
			} /* end of rdp_rule_7 */
			else
			{
				/* default action processing for rdp_rule_7 */
				if (rdp_tree_update)
				{
					RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
					temp.data.id = 0;
					temp.data.token = SCAN_P_ID;
				}
			}
			type = text_insert_string("void");
			stars = 0;
			if (scan_test(null, RDP_T_58 /* : */, null))
			{ /* Start of rdp_rule_11 */
				while (true)
				{
					{
						if (rdp_tree_update)
						{
							rdp_add_child(null, rdp_tree);
						}
						scan_test(null, RDP_T_58 /* : */, rule_stop);
						scan_();
						if (rdp_tree_update)
						{
							rdp_add_child(null, rdp_tree);
						}
						scan_test(null, SCAN_P_ID, rule_stop);
						type = text_scan_data.id;
						scan_();
						if (scan_test(null, RDP_T_42 /* * */, null))
						{ /* Start of rdp_rule_9 */
							while (true)
							{
								{
									if (rdp_tree_update)
									{
										rdp_add_child(null, rdp_tree);
									}
									scan_test(null, RDP_T_42 /* * */, rule_stop);
									scan_();
									stars++;
								}
								if (!scan_test(null, RDP_T_42 /* * */, null))
								{
									break;
								}
							}
						} /* end of rdp_rule_9 */
						else
						{
							/* default action processing for rdp_rule_9 */
							if (rdp_tree_update)
							{
								RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
								temp.data.id = 0;
								temp.data.token = SCAN_P_ID;
							}
						}
					}
					break; /* hi limit is 1! */
				}
			} /* end of rdp_rule_11 */
			else
			{
				/* default action processing for rdp_rule_11 */
				if (rdp_tree_update)
				{
					RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
					temp.data.id = 0;
					temp.data.token = SCAN_P_ID;
				}
			}
			if (rdp_pass == 2)
			{
				result.promote_default = PROMOTE_DONT;
			}
			if (scan_test_set(null, rdp_rule_16_first, null))
			{ /* Start of rdp_rule_16 */
				while (true)
				{
					{
						if (scan_test(null, RDP_T_9495 /* ^_ */, null))
						{
							if (rdp_tree_update)
							{
								rdp_add_child(null, rdp_tree);
							}
							scan_test(null, RDP_T_9495 /* ^_ */, rule_stop);
							scan_();
							if (rdp_pass == 2)
							{
								result.promote_default = PROMOTE_DONT;
							}
						}
						else if (scan_test(null, RDP_T_94 /* ^ */, null))
						{
							if (rdp_tree_update)
							{
								rdp_add_child(null, rdp_tree);
							}
							scan_test(null, RDP_T_94 /* ^ */, rule_stop);
							scan_();
							if (rdp_pass == 2)
							{
								result.promote_default = PROMOTE;
							}
						}
						else if (scan_test(null, RDP_T_9494 /* ^^ */, null))
						{
							if (rdp_tree_update)
							{
								rdp_add_child(null, rdp_tree);
							}
							scan_test(null, RDP_T_9494 /* ^^ */, rule_stop);
							scan_();
							if (rdp_pass == 2)
							{
								result.promote_default = PROMOTE_AND_COPY;
							}
						}
						else if (scan_test(null, RDP_T_949494 /* ^^^ */, null))
						{
							if (rdp_tree_update)
							{
								rdp_add_child(null, rdp_tree);
							}
							scan_test(null, RDP_T_949494 /* ^^^ */, rule_stop);
							scan_();
							if (rdp_pass == 2)
							{
								result.promote_default = PROMOTE_ABOVE;
							}
						}
						else
						{
							scan_test_set(null, rdp_rule_16_first, rule_stop);
						}
					}
					break; /* hi limit is 1! */
				}
			} /* end of rdp_rule_16 */
			else
			{
				/* default action processing for rdp_rule_16 */
				if (rdp_tree_update)
				{
					RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
					temp.data.id = 0;
					temp.data.token = SCAN_P_ID;
				}
			}
			rdp_comment_only = true; /* set comments only flag */
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_585861 /* ::= */, rule_stop);
			scan_();
			body = prod(rdp_add_child("prod", rdp_tree));
			if (rdp_pass == 2)
			{
				rdp_rule_count++;
				result.list = body;
				result.been_defined = 1;
				result.return_type = text_get_string(type);
				result.return_type_stars = stars;
				result.comment_only = rdp_comment_only;
			}
			if (rdp_tree_update)
			{
				rdp_add_child(null, rdp_tree);
			}
			scan_test(null, RDP_T_46 /* . */, rule_stop);
			scan_();
			scan_test_set(null, rule_stop, rule_stop);
		}
		return result;
	}

	private static RdpList seq(RdpTreeNodeData rdp_tree)
	{
		RdpList result = null;
		RdpData body = null;
		String ret_name = null;
		String default_action = null;
		RdpList end = null;
		int promote_op = 0;
		int promote_epsilon = 0;
		if (rdp_pass == 2)
		{
			end = result = new RdpList();
		}
		/* Start of rdp_seq_31 */
		while (true)
		{
			scan_test_set(null, rdp_seq_31_first, seq_stop);

			if (rdp_pass == 2)
			{
				ret_name = null;
				promote_epsilon = PROMOTE_DONT;
				promote_op = PROMOTE_DEFAULT;
			}
			/* Start of rdp_seq_29 */
			while (true)
			{
				scan_test_set(null, rdp_seq_29_first, seq_stop);
				if (scan_test_set(null, rdp_seq_10_first, null))
				{
					/* Start of rdp_seq_2 */
					while (true)
					{
						scan_test_set(null, rdp_seq_2_first, seq_stop);
						if (scan_test_set(null, rdp_seq_0_first, null))
						{
							body = item_ret(rdp_add_child("item_ret", rdp_tree));
							rdp_comment_only = false;
						}
						else if (scan_test_set(null, rdp_seq_1_first, null))
						{
							body = item_com(rdp_add_child("item_com", rdp_tree));
						}
						else
						{
							scan_test_set(null, rdp_seq_2_first, seq_stop);
						}
						break; /* hi limit is 1! */
					}
					/* end of rdp_seq_2 */
					if (scan_test(null, RDP_T_58 /* : */, null))
					{ /* Start of rdp_seq_4 */
						while (true)
						{
							if (rdp_tree_update)
							{
								rdp_add_child(null, rdp_tree);
							}
							scan_test(null, RDP_T_58 /* : */, seq_stop);
							scan_();
							if (rdp_tree_update)
							{
								rdp_add_child(null, rdp_tree);
							}
							scan_test(null, SCAN_P_ID, seq_stop);
							ret_name = text_get_string(text_scan_data.id);
							scan_();
							break; /* hi limit is 1! */
						}
					} /* end of rdp_seq_4 */
					else
					{
						/* default action processing for rdp_seq_4 */
						if (rdp_tree_update)
						{
							RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
							temp.data.id = 0;
							temp.data.token = SCAN_P_ID;
						}
					}
					if (scan_test_set(null, rdp_seq_9_first, null))
					{ /* Start of rdp_seq_9 */
						while (true)
						{
							if (scan_test(null, RDP_T_9495 /* ^_ */, null))
							{
								if (rdp_tree_update)
								{
									rdp_add_child(null, rdp_tree);
								}
								scan_test(null, RDP_T_9495 /* ^_ */, seq_stop);
								scan_();
								if (rdp_pass == 2)
								{
									promote_op = PROMOTE_DONT;
								}
							}
							else if (scan_test(null, RDP_T_94 /* ^ */, null))
							{
								if (rdp_tree_update)
								{
									rdp_add_child(null, rdp_tree);
								}
								scan_test(null, RDP_T_94 /* ^ */, seq_stop);
								scan_();
								if (rdp_pass == 2)
								{
									promote_op = PROMOTE;
								}
							}
							else if (scan_test(null, RDP_T_9494 /* ^^ */, null))
							{
								if (rdp_tree_update)
								{
									rdp_add_child(null, rdp_tree);
								}
								scan_test(null, RDP_T_9494 /* ^^ */, seq_stop);
								scan_();
								if (rdp_pass == 2)
								{
									promote_op = PROMOTE_AND_COPY;
								}
							}
							else if (scan_test(null, RDP_T_949494 /* ^^^ */, null))
							{
								if (rdp_tree_update)
								{
									rdp_add_child(null, rdp_tree);
								}
								scan_test(null, RDP_T_949494 /* ^^ ^ */, seq_stop);
								scan_();
								if (rdp_pass == 2)
								{
									promote_op = PROMOTE_ABOVE;
								}
							}
							else
							{
								scan_test_set(null, rdp_seq_9_first, seq_stop);
							}
							break; /* hi limit is 1! */
						}
					} /* end of rdp_seq_9 */
					else
					{
						/* default action processing for rdp_seq_9 */
						if (rdp_tree_update)
						{
							RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
							temp.data.id = 0;
							temp.data.token = SCAN_P_ID;
						}
					}
				}
				else if (scan_test_set(null, rdp_seq_28_first, null))
				{
					body = item_inl(rdp_add_child("item_inl", rdp_tree));
					rdp_comment_only = false;
					if (rdp_pass == 2)
					{
						promote_op = body.promote;
						default_action = null;
					}
					if (scan_test(null, RDP_T_58 /* : */, null))
					{ /* Start of rdp_seq_27 */
						while (true)
						{
							if (rdp_tree_update)
							{
								rdp_add_child(null, rdp_tree);
							}
							scan_test(null, RDP_T_58 /* : */, seq_stop);
							scan_();
							if (scan_test(null, SCAN_P_ID, null))
							{ /* Start of rdp_seq_12 */
								while (true)
								{
									if (rdp_tree_update)
									{
										rdp_add_child(null, rdp_tree);
									}
									scan_test(null, SCAN_P_ID, seq_stop);
									scan_();
									if (rdp_pass == 2)
									{
										text_message(TEXT_ERROR_ECHO,
												"Illegal grammar element: expressions may not retun a value");
									}
									break; /* hi limit is 1! */
								}
							} /* end of rdp_seq_12 */
							else
							{
								/* default action processing for rdp_seq_12 */
								if (rdp_tree_update)
								{
									RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
									temp.data.id = 0;
									temp.data.token = SCAN_P_ID;
								}
							}
							if (scan_test_set(null, rdp_seq_25_first, null))
							{ /* Start of rdp_seq_25 */
								while (true)
								{
									if (scan_test(null, RDP_T_9142 /* [ * */, null))
									{
										default_action = code(rdp_add_child("code", rdp_tree));
										if (scan_test_set(null, rdp_seq_17_first, null))
										{ /* Start of rdp_seq_17 */
											while (true)
											{
												if (scan_test(null, RDP_T_9495 /* ^ _ */, null))
												{
													if (rdp_tree_update)
													{
														rdp_add_child(null, rdp_tree);
													}
													scan_test(null, RDP_T_9495 /* ^ _ */, seq_stop);
													scan_();
													if (rdp_pass == 2)
													{
														promote_epsilon = PROMOTE_DONT;
													}
												}
												else if (scan_test(null, RDP_T_94 /* ^ */, null))
												{
													if (rdp_tree_update)
													{
														rdp_add_child(null, rdp_tree);
													}
													scan_test(null, RDP_T_94 /* ^ */, seq_stop);
													scan_();
													if (rdp_pass == 2)
													{
														promote_epsilon = PROMOTE;
													}
												}
												else if (scan_test(null, RDP_T_9494 /* ^ ^ */, null))
												{
													if (rdp_tree_update)
													{
														rdp_add_child(null, rdp_tree);
													}
													scan_test(null, RDP_T_9494 /* ^ ^ */, seq_stop);
													scan_();
													if (rdp_pass == 2)
													{
														promote_epsilon = PROMOTE_AND_COPY;
													}
												}
												else if (scan_test(null, RDP_T_949494 /* ^ ^ ^ */, null))
												{
													if (rdp_tree_update)
													{
														rdp_add_child(null, rdp_tree);
													}
													scan_test(null, RDP_T_949494 /* ^ ^ ^ */, seq_stop);
													scan_();
													if (rdp_pass == 2)
													{
														promote_epsilon = PROMOTE_ABOVE;
													}
												}
												else
												{
													scan_test_set(null, rdp_seq_17_first, seq_stop);
												}
												break; /* hi limit is 1 ! */
											}
										} /* end of rdp_seq_17 */
										else
										{
											/* default action processing for rdp_seq_17 */
											if (rdp_tree_update)
											{
												RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
												temp.data.id = 0;
												temp.data.token = SCAN_P_ID;
											}
										}
									}
									else if (scan_test_set(null, rdp_seq_24_first, null))
									{
										/* Start of rdp_seq_23 */
										while (true)
										{
											scan_test_set(null, rdp_seq_23_first, seq_stop);
											if (scan_test(null, RDP_T_9495 /* ^ _ */, null))
											{
												if (rdp_tree_update)
												{
													rdp_add_child(null, rdp_tree);
												}
												scan_test(null, RDP_T_9495 /* ^ _ */, seq_stop);
												scan_();
												if (rdp_pass == 2)
												{
													promote_epsilon = PROMOTE_DONT;
												}
											}
											else if (scan_test(null, RDP_T_94 /* ^ */, null))
											{
												if (rdp_tree_update)
												{
													rdp_add_child(null, rdp_tree);
												}
												scan_test(null, RDP_T_94 /* ^ */, seq_stop);
												scan_();
												if (rdp_pass == 2)
												{
													promote_epsilon = PROMOTE;
												}
											}
											else if (scan_test(null, RDP_T_9494 /* ^ ^ */, null))
											{
												if (rdp_tree_update)
												{
													rdp_add_child(null, rdp_tree);
												}
												scan_test(null, RDP_T_9494 /* ^ ^ */, seq_stop);
												scan_();
												if (rdp_pass == 2)
												{
													promote_epsilon = PROMOTE_AND_COPY;
												}
											}
											else if (scan_test(null, RDP_T_949494 /* ^ ^ ^ */, null))
											{
												if (rdp_tree_update)
												{
													rdp_add_child(null, rdp_tree);
												}
												scan_test(null, RDP_T_949494 /* ^ ^ ^ */, seq_stop);
												scan_();
												if (rdp_pass == 2)
												{
													promote_epsilon = PROMOTE_ABOVE;
												}
											}
											else
											{
												scan_test_set(null, rdp_seq_23_first, seq_stop);
											}
											break; /* hi limit is 1 ! */
										}
										/* end of rdp_seq_23 */
									}
									else
									{
										scan_test_set(null, rdp_seq_25_first, seq_stop);
									}
									break; /* hi limit is 1! */
								}
							} /* end of rdp_seq_25 */
							else
							{
								/* default action processing for rdp_seq_25 */
								if (rdp_tree_update)
								{
									RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
									temp.data.id = 0;
									temp.data.token = SCAN_P_ID;
								}
							}
							break; /* hi limit is 1! */
						}
					} /* end of rdp_seq_27 */
					else
					{
						/* default action processing for rdp_seq_27 */
						if (rdp_tree_update)
						{
							RdpTreeNodeData temp = rdp_add_child(null, rdp_tree);
							temp.data.id = 0;
							temp.data.token = SCAN_P_ID;
						}
					}
				}
				else
				{
					scan_test_set(null, rdp_seq_29_first, seq_stop);
				}
				break; /* hi limit is 1! */
			}
			/* end of rdp_seq_29 */
			if (rdp_pass == 2)
			{
				end.next = new RdpList();
				end = end.next;
				end.production = body;
				end.actuals = body.actuals;
				end.return_name = ret_name;
				end.promote = promote_op;
				end.promote_epsilon = promote_epsilon;
				end.default_action = default_action;

			}
			if (!scan_test_set(null, rdp_seq_31_first, null))
			{
				break;
			}
		}
		/* end of rdp_seq_31 */
		if (rdp_pass == 2)
		{
			result = result.next;
		}
		scan_test_set(null, seq_stop, seq_stop);
		return result;
	}

	private static String String(RdpTreeNodeData rdp_tree)
	{
		String result;

		if (rdp_tree_update)
		{
			rdp_add_child(null, rdp_tree);
		}
		scan_test(null, RDP_T_34 /* " */, String_stop);
		result = text_get_string(text_scan_data.id);
		scan_();
		scan_test_set(null, String_stop, String_stop);
		return result;
	}

	private static String token(RdpTreeNodeData rdp_tree)
	{
		if (rdp_tree_update)
		{
			rdp_add_child(null, rdp_tree);
		}
		scan_test(null, RDP_T_39 /* ' */, token_stop);
		String result = text_get_string(text_scan_data.id);
		scan_();
		scan_test_set(null, token_stop, token_stop);
		return result;
	}
}
