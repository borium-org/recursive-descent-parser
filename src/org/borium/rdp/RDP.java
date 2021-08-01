package org.borium.rdp;

import static org.borium.rdp.Arg.*;
import static org.borium.rdp.RdpAux.*;
import static org.borium.rdp.Scan.*;
import static org.borium.rdp.Text.*;
import static org.borium.rdp.Text.TextMessageType.*;

import java.text.*;
import java.util.*;

public class RDP
{
	private static final String __DATE__ = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
	private static final String __TIME__ = new SimpleDateFormat("HH:mm:ss").format(new Date());

	private static final String RDP_STAMP = "Generated on Sep 19 2015 11:45:00 and compiled on " + __DATE__ + " at "
			+ __TIME__;

	@SuppressWarnings("unused")
	private static String rdp_sourcefilename; // current source file name
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
		// if (rdp_lexicalise)
		// scan_lexicalise();
		// locals = symbol_new_table("locals", 101, 31, symbol_compare_string, symbol_hash_string, symbol_print_string);
		// codes = symbol_new_table("codes", 101, 31, symbol_compare_string, symbol_hash_string, symbol_print_string);
		// tokens = symbol_new_table("tokens", 101, 31, symbol_compare_string, symbol_hash_string, symbol_print_string);
		// rdp = symbol_new_table("rdp", 101, 31, symbol_compare_string, symbol_hash_string, symbol_print_string);
		// rdp_set_initialise();
		// rdp_load_keywords();
		// rdp_pre_parse();
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
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}
}
