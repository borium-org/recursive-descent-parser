package org.borium.rdp;

import static org.borium.rdp.CRT.*;
import static org.borium.rdp.Text.*;
import static org.borium.rdp.Text.TextMessageType.*;

public class RdpGram
{
	public static void rdp_check_token_valid(String id)
	{
		if (id == null)
			return;

		if (id.length() == 0)
			text_message(TEXT_ERROR_ECHO, "empty tokens are not allowed: use [ ... ] instead\n");
		// Test for embedded spaces in token

		boolean bad = false;

		for (char ch : id.toCharArray())
		{
			bad |= !isgraph(ch);
		}

		if (bad)
			text_message(TEXT_ERROR_ECHO, "tokens must not contain spaces or control characters\n");
	}
}
