package org.borium.rdp;

import static org.borium.rdp.Symbol.*;
import static org.borium.rdp.Text.*;

public class Scan
{
	public static class ScanData extends Symbol
	{
		int token;
		int extended;
		ScanCommentBlock comment_block;
		String sourcefilename;
		int line_number;
		int u;
		boolean i;
		double r;
		Object p;
	}

	private static class ScanCommentBlock
	{
		@SuppressWarnings("unused")
		String comment;
		@SuppressWarnings("unused")
		int column;
		@SuppressWarnings("unused")
		int sequence_number;
		@SuppressWarnings("unused")
		ScanCommentBlock next;
		@SuppressWarnings("unused")
		ScanCommentBlock previous;
	}

	static final int SCAN_P_IGNORE = 0;
	static final int SCAN_P_ID = 1;
	static final int SCAN_P_INTEGER = 2;
	static final int SCAN_P_REAL = 3;
	static final int SCAN_P_CHAR = 4;
	static final int SCAN_P_CHAR_ESC = 5;
	static final int SCAN_P_STRING = 6;
	static final int SCAN_P_STRING_ESC = 7;
	static final int SCAN_P_COMMENT = 8;
	static final int SCAN_P_COMMENT_VISIBLE = 9;
	static final int SCAN_P_COMMENT_NEST = 10;
	static final int SCAN_P_COMMENT_NEST_VISIBLE = 11;
	static final int SCAN_P_COMMENT_LINE = 12;
	static final int SCAN_P_COMMENT_LINE_VISIBLE = 13;
	static final int SCAN_P_EOF = 14;
	static final int SCAN_P_EOLN = 15;
	static final int SCAN_P_TOP = 16;

	@SuppressWarnings("unused")
	private static boolean scan_case_insensitive = false;
	@SuppressWarnings("unused")
	private static boolean scan_show_skips = false;
	@SuppressWarnings("unused")
	private static boolean scan_newline_visible = false;
	@SuppressWarnings("unused")
	private static boolean scan_symbol_echo = false;
	@SuppressWarnings("unused")
	private static String[] scan_token_names = null;
	private static ScanCommentBlock scan_comment_list = null;
	private static ScanCommentBlock scan_comment_list_end = null;
	@SuppressWarnings("unused")
	private static ScanCommentBlock last_comment_block;
	@SuppressWarnings("unused")
	private static SymbolTable scan_table;
	@SuppressWarnings("unused")
	private static boolean scan_lexicalise_flag = false;

	public static void scan_init(boolean case_insensitive, boolean newline_visible, boolean show_skips,
			boolean symbol_echo, String[] token_names)
	{
		scan_case_insensitive = case_insensitive;
		scan_show_skips = show_skips;
		scan_newline_visible = newline_visible;
		scan_symbol_echo = symbol_echo;
		scan_token_names = token_names;

		scan_comment_list = new ScanCommentBlock();
		scan_comment_list_end = scan_comment_list;
		text_scan_data = new ScanData();
		scan_table = symbol_new_table("scan table", 101, 31, new CompareHashPrint());
		scan_insert_comment_block("", 0, 0);
	}

	public static void scan_lexicalise()
	{
		scan_lexicalise_flag = true;
	}

	public static void scan_load_keyword(String id1, String id2, int token, int extended)
	{
		ScanData d = new ScanData();
		d.id = text_insert_string(id1);
		if (id2 != null)
		{
			text_insert_string(id2);
		}
		d.token = token;
		d.extended = extended;
		symbol_insert_symbol(scan_table, d);
	}

	private static void scan_insert_comment_block(String pattern, int column, int sequence_number)
	{
		ScanCommentBlock temp = new ScanCommentBlock();
		scan_comment_list_end.comment = pattern;
		scan_comment_list_end.sequence_number = sequence_number;
		scan_comment_list_end.column = column;
		temp.previous = scan_comment_list_end;
		scan_comment_list_end.next = temp;
		scan_comment_list_end = temp;
		last_comment_block = temp;
	}
}
