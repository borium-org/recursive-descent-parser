package org.borium.rdp;

import static org.borium.rdp.Symbol.*;
import static org.borium.rdp.Text.*;

public class Scan
{
	public static class ScanData
	{
		String id;
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
