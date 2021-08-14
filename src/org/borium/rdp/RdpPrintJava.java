package org.borium.rdp;

import static org.borium.rdp.RDP.*;
import static org.borium.rdp.RdpAux.*;
import static org.borium.rdp.Text.*;

import java.io.*;

import org.borium.rdp.RdpAux.*;

public class RdpPrintJava extends RdpPrint
{
	/** Path where the files will be written */
	@SuppressWarnings("unused")
	private String outputPath;
	/** Package for all generated classes */
	@SuppressWarnings("unused")
	private String classPackage;

	/**
	 * @param outputPath
	 *                     Path where the files will be written.
	 * @param classPackage
	 *                     Package for all generated classes.
	 */
	public RdpPrintJava(String outputPath, String classPackage)
	{
		this.outputPath = outputPath;
		this.classPackage = classPackage;
	}

	/**
	 * @param rdp_base
	 *                    The root of RDP data structures for the parser
	 * @param parser_only
	 *                    True to generate only the parser, false to generate AST classes
	 */
	public void print(SymbolScopeData rdp_base, boolean parser_only)
	{
		printKeywordJava();
	}

	@Override
	protected int indent()
	{
		for (int temp = 0; temp < rdp_indentation; temp++)
		{
			text_printf("\t");
		}
		return rdp_indentation * 4;
	}

	private PrintStream createFile(String className)
	{
		PrintStream file = null;
		try
		{
			String classPath = classPackage.replace('.', '/');
			File f = new File(outputPath + "/" + classPath);
			f.mkdirs();
			file = new PrintStream(outputPath + "/" + classPath + "/" + className + ".java");
			rdp_indentation = 0;
		}
		catch (FileNotFoundException e)
		{
			throw new RuntimeException();
		}
		return file;
	}

	private void printKeywordJava()
	{
		PrintStream file = createFile("Keyword");

		text_redirect(file);

		iprintln("package " + classPackage + ";");
		iprintln();

		iprintln("public enum Keyword");
		iprintln("{");
		rdp_indentation++;
		iprintln("SCAN_P_EOF(\"<EOF>\"),");
		iprintln("SCAN_P_ID(\"<Ident>\"),");
		iprintln("SCAN_P_ERROR(\"<Error>\"),");
		iprintln("SCAN_P_INTEGER(\"<Integer>\"),");
		iprintln("SCAN_P_REAL(\"<Float>\"),");
		RdpData temp = (RdpData) tokens.getScope().nextSymbolInScope();
		while (temp != null)
		{
			if (temp.kind == K_TOKEN || temp.kind == K_EXTENDED)
			{
				iprint("");
				rdp_print_parser_production_name_no_comment(temp);
				String id = "";
				String tempId = text_get_string(temp.id);
				for (char ch : tempId.toCharArray())
				{
					if (ch == '"')
						id += "\\\"";
					else
						id += ch;
				}
				if (id.equals("\\\""))
					id = "<String>";
				if (id.equals("\'"))
					id = "<Char>";
				text_printf("(\"" + id + "\"),");
				iprintln();
			}
			temp = (RdpData) temp.nextSymbolInScope();
		}
		iprintln("kw__last(null);");
		iprintln();
		iprintln("private String text;");
		iprintln();
		iprintln("private Keyword(String string)");
		iprintln("{");
		iprintln("\ttext = string;");
		iprintln("}");
		iprintln();
		iprintln("@Override");
		iprintln("public String toString()");
		iprintln("{");
		iprintln("\treturn text;");
		iprintln("}");
		rdp_indentation--;
		iprintln("}");
		text_redirect(System.out);
		file.close();
	}
}
