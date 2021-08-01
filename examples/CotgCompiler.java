package cotg;

import static cotg.CotgKeyword.*;
import static cotg.Main.*;
import cotg.ast.*;

public class CotgCompiler extends CotgCompilerSets
{
	private static CotgCompiler instance;

	public static String getLastId()
	{
		return instance.scanner.getLastId();
	}

	public CotgCompiler()
	{
		super(new CotgScanner());
		instance = this;
	}

	@Override
	public boolean Compile()
	{
		printDateTime("Scanning        ");
		AstDeobfuscator ast = Deobfuscator();
		printDateTime("Processing      ");
		ast.process();
		printDateTime("Done            ");
		return true;
	}

	protected AstAddExpression AddExpression()
	{
		AstAddExpression ast = new AstAddExpression();
		ast.add(MultiplyExpression());
		if (scanner.test("rdp_AddExpression_1", rdp_AddExpression_1_first, null))
		{
			while (true)
			{
				ast.add(AddOp());
				ast.add(MultiplyExpression());
				if (!scanner.test("rdp_AddExpression_1", rdp_AddExpression_1_first, null))
				{
					break;
				}
			}
		}
		scanner.test("AddExpression", AddExpression_stop, AddExpression_stop);
		return ast;
	}

	protected AstAddOp AddOp()
	{
		AstAddOp ast = new AstAddOp();
		if (scanner.test("rdp_AddOp_0", RDP_T_43 /* + */, null))
		{
			scanner.test("AddOp", RDP_T_43 /* + */, AddOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_AddOp_1", RDP_T_45 /* - */, null))
		{
			scanner.test("AddOp", RDP_T_45 /* - */, AddOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else
		{
			scanner.test("AddOp", AddOp_first, AddOp_stop);
		}
		scanner.test("AddOp", AddOp_stop, AddOp_stop);
		return ast;
	}

	protected AstAssignmentExpression AssignmentExpression()
	{
		AstAssignmentExpression ast = new AstAssignmentExpression();
		ast.add(BoolOrExpression());
		if (scanner.test("rdp_AssignmentExpression_1", rdp_AssignmentExpression_1_first, null))
		{
			while (true)
			{
				ast.add(AssignmentOp());
				ast.add(Expression());
				if (!scanner.test("rdp_AssignmentExpression_1", rdp_AssignmentExpression_1_first, null))
				{
					break;
				}
			}
		}
		scanner.test("AssignmentExpression", AssignmentExpression_stop, AssignmentExpression_stop);
		return ast;
	}

	protected AstAssignmentOp AssignmentOp()
	{
		AstAssignmentOp ast = new AstAssignmentOp();
		if (scanner.test("rdp_AssignmentOp_0", RDP_T_61 /* = */, null))
		{
			scanner.test("AssignmentOp", RDP_T_61 /* = */, AssignmentOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_AssignmentOp_1", RDP_T_4261 /* *= */, null))
		{
			scanner.test("AssignmentOp", RDP_T_4261 /* *= */, AssignmentOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_AssignmentOp_2", RDP_T_4761 /* /= */, null))
		{
			scanner.test("AssignmentOp", RDP_T_4761 /* /= */, AssignmentOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_AssignmentOp_3", RDP_T_3761 /* %= */, null))
		{
			scanner.test("AssignmentOp", RDP_T_3761 /* %= */, AssignmentOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_AssignmentOp_4", RDP_T_4361 /* += */, null))
		{
			scanner.test("AssignmentOp", RDP_T_4361 /* += */, AssignmentOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_AssignmentOp_5", RDP_T_4561 /* -= */, null))
		{
			scanner.test("AssignmentOp", RDP_T_4561 /* -= */, AssignmentOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_AssignmentOp_6", RDP_T_606061 /* <<= */, null))
		{
			scanner.test("AssignmentOp", RDP_T_606061 /* <<= */, AssignmentOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_AssignmentOp_7", RDP_T_626261 /* >>= */, null))
		{
			scanner.test("AssignmentOp", RDP_T_626261 /* >>= */, AssignmentOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_AssignmentOp_8", RDP_T_62626261 /* >>>= */, null))
		{
			scanner.test("AssignmentOp", RDP_T_62626261 /* >>>= */, AssignmentOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_AssignmentOp_9", RDP_T_3861 /* &= */, null))
		{
			scanner.test("AssignmentOp", RDP_T_3861 /* &= */, AssignmentOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_AssignmentOp_10", RDP_T_9461 /* ^= */, null))
		{
			scanner.test("AssignmentOp", RDP_T_9461 /* ^= */, AssignmentOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_AssignmentOp_11", RDP_T_12461 /* |= */, null))
		{
			scanner.test("AssignmentOp", RDP_T_12461 /* |= */, AssignmentOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else
		{
			scanner.test("AssignmentOp", AssignmentOp_first, AssignmentOp_stop);
		}
		scanner.test("AssignmentOp", AssignmentOp_stop, AssignmentOp_stop);
		return ast;
	}

	protected AstAssignmentStatement AssignmentStatement()
	{
		AstAssignmentStatement ast = new AstAssignmentStatement();
		ast.add(BoolOrExpression());
		if (scanner.test("rdp_AssignmentStatement_4", rdp_AssignmentStatement_4_first, null))
		{
			while (true)
			{
				scanner.test("rdp_AssignmentStatement_2", rdp_AssignmentStatement_2_first, AssignmentStatement_stop);
				if (scanner.test("rdp_AssignmentStatement_0", rdp_AssignmentStatement_0_first, null))
				{
					ast.add(AssignmentOp());
					ast.add(Expression());
				}
				else if (scanner.test("rdp_AssignmentStatement_1", RDP_T_123 /* { */, null))
				{
					scanner.test("AssignmentStatement", RDP_T_123 /* { */, AssignmentStatement_stop);
					ast.add(lastsym);
					getsym();
					scanner.test("AssignmentStatement", RDP_T_125 /* } */, AssignmentStatement_stop);
					ast.add(lastsym);
					getsym();
				}
				else
				{
					scanner.test("rdp_AssignmentStatement_2", rdp_AssignmentStatement_2_first, AssignmentStatement_stop);
				}
				if (!scanner.test("rdp_AssignmentStatement_4", rdp_AssignmentStatement_4_first, null))
				{
					break;
				}
			}
		}
		if (scanner.test("rdp_AssignmentStatement_6", RDP_T_44 /* , */, null))
		{
			while (true)
			{
				scanner.test("AssignmentStatement", RDP_T_44 /* , */, AssignmentStatement_stop);
				ast.add(lastsym);
				getsym();
				ast.add(AssignmentStatement());
				if (!scanner.test("rdp_AssignmentStatement_6", RDP_T_44 /* , */, null))
				{
					break;
				}
			}
		}
		scanner.test("AssignmentStatement", AssignmentStatement_stop, AssignmentStatement_stop);
		return ast;
	}

	protected AstBitAndExpression BitAndExpression()
	{
		AstBitAndExpression ast = new AstBitAndExpression();
		ast.add(EqualsExpression());
		if (scanner.test("rdp_BitAndExpression_1", RDP_T_38 /* & */, null))
		{
			while (true)
			{
				ast.add(BitAndOp());
				ast.add(EqualsExpression());
				if (!scanner.test("rdp_BitAndExpression_1", RDP_T_38 /* & */, null))
				{
					break;
				}
			}
		}
		scanner.test("BitAndExpression", BitAndExpression_stop, BitAndExpression_stop);
		return ast;
	}

	protected AstBitAndOp BitAndOp()
	{
		AstBitAndOp ast = new AstBitAndOp();
		scanner.test("BitAndOp", RDP_T_38 /* & */, BitAndOp_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("BitAndOp", BitAndOp_stop, BitAndOp_stop);
		return ast;
	}

	protected AstBitOrExpression BitOrExpression()
	{
		AstBitOrExpression ast = new AstBitOrExpression();
		ast.add(BitXorExpression());
		if (scanner.test("rdp_BitOrExpression_1", RDP_T_124 /* | */, null))
		{
			while (true)
			{
				ast.add(BitOrOp());
				ast.add(BitXorExpression());
				if (!scanner.test("rdp_BitOrExpression_1", RDP_T_124 /* | */, null))
				{
					break;
				}
			}
		}
		scanner.test("BitOrExpression", BitOrExpression_stop, BitOrExpression_stop);
		return ast;
	}

	protected AstBitOrOp BitOrOp()
	{
		AstBitOrOp ast = new AstBitOrOp();
		scanner.test("BitOrOp", RDP_T_124 /* | */, BitOrOp_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("BitOrOp", BitOrOp_stop, BitOrOp_stop);
		return ast;
	}

	protected AstBitXorExpression BitXorExpression()
	{
		AstBitXorExpression ast = new AstBitXorExpression();
		ast.add(BitAndExpression());
		if (scanner.test("rdp_BitXorExpression_1", RDP_T_94 /* ^ */, null))
		{
			while (true)
			{
				ast.add(BitXorOp());
				ast.add(BitAndExpression());
				if (!scanner.test("rdp_BitXorExpression_1", RDP_T_94 /* ^ */, null))
				{
					break;
				}
			}
		}
		scanner.test("BitXorExpression", BitXorExpression_stop, BitXorExpression_stop);
		return ast;
	}

	protected AstBitXorOp BitXorOp()
	{
		AstBitXorOp ast = new AstBitXorOp();
		scanner.test("BitXorOp", RDP_T_94 /* ^ */, BitXorOp_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("BitXorOp", BitXorOp_stop, BitXorOp_stop);
		return ast;
	}

	protected AstBlock Block()
	{
		AstBlock ast = new AstBlock();
		scanner.test("Block", RDP_T_123 /* { */, Block_stop);
		ast.add(lastsym);
		getsym();
		if (scanner.test("rdp_Block_1", rdp_Block_1_first, null))
		{
			while (true)
			{
				ast.add(Statement());
				if (!scanner.test("rdp_Block_1", rdp_Block_1_first, null))
				{
					break;
				}
			}
		}
		scanner.test("Block", RDP_T_125 /* } */, Block_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("Block", Block_stop, Block_stop);
		return ast;
	}

	protected AstBlockOrStatement BlockOrStatement()
	{
		AstBlockOrStatement ast = new AstBlockOrStatement();
		if (scanner.test("rdp_BlockOrStatement_0", RDP_T_123 /* { */, null))
		{
			ast.add(Block());
		}
		else if (scanner.test("rdp_BlockOrStatement_1", rdp_BlockOrStatement_1_first, null))
		{
			ast.add(Statement());
		}
		else
		{
			scanner.test("BlockOrStatement", BlockOrStatement_first, BlockOrStatement_stop);
		}
		scanner.test("BlockOrStatement", BlockOrStatement_stop, BlockOrStatement_stop);
		return ast;
	}

	protected AstBoolAndExpression BoolAndExpression()
	{
		AstBoolAndExpression ast = new AstBoolAndExpression();
		ast.add(BitOrExpression());
		if (scanner.test("rdp_BoolAndExpression_1", RDP_T_3838 /* && */, null))
		{
			while (true)
			{
				ast.add(BoolAndOp());
				ast.add(BitOrExpression());
				if (!scanner.test("rdp_BoolAndExpression_1", RDP_T_3838 /* && */, null))
				{
					break;
				}
			}
		}
		scanner.test("BoolAndExpression", BoolAndExpression_stop, BoolAndExpression_stop);
		return ast;
	}

	protected AstBoolAndOp BoolAndOp()
	{
		AstBoolAndOp ast = new AstBoolAndOp();
		scanner.test("BoolAndOp", RDP_T_3838 /* && */, BoolAndOp_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("BoolAndOp", BoolAndOp_stop, BoolAndOp_stop);
		return ast;
	}

	protected AstBoolOrExpression BoolOrExpression()
	{
		AstBoolOrExpression ast = new AstBoolOrExpression();
		ast.add(BoolAndExpression());
		if (scanner.test("rdp_BoolOrExpression_4", RDP_T_124124 /* || */, null))
		{
			while (true)
			{
				ast.add(BoolOrOp());
				scanner.test("rdp_BoolOrExpression_2", rdp_BoolOrExpression_2_first, BoolOrExpression_stop);
				if (scanner.test("rdp_BoolOrExpression_0", RDP_T_123 /* { */, null))
				{
					scanner.test("BoolOrExpression", RDP_T_123 /* { */, BoolOrExpression_stop);
					ast.add(lastsym);
					getsym();
					scanner.test("BoolOrExpression", RDP_T_125 /* } */, BoolOrExpression_stop);
					ast.add(lastsym);
					getsym();
				}
				else if (scanner.test("rdp_BoolOrExpression_1", rdp_BoolOrExpression_1_first, null))
				{
					ast.add(BoolAndExpression());
				}
				else
				{
					scanner.test("rdp_BoolOrExpression_2", rdp_BoolOrExpression_2_first, BoolOrExpression_stop);
				}
				if (!scanner.test("rdp_BoolOrExpression_4", RDP_T_124124 /* || */, null))
				{
					break;
				}
			}
		}
		scanner.test("BoolOrExpression", BoolOrExpression_stop, BoolOrExpression_stop);
		return ast;
	}

	protected AstBoolOrOp BoolOrOp()
	{
		AstBoolOrOp ast = new AstBoolOrOp();
		scanner.test("BoolOrOp", RDP_T_124124 /* || */, BoolOrOp_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("BoolOrOp", BoolOrOp_stop, BoolOrOp_stop);
		return ast;
	}

	protected AstBreakStatement BreakStatement()
	{
		AstBreakStatement ast = new AstBreakStatement();
		scanner.test("BreakStatement", RDP_T_break, BreakStatement_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("BreakStatement", RDP_T_59 /* ; */, BreakStatement_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("BreakStatement", BreakStatement_stop, BreakStatement_stop);
		return ast;
	}

	protected AstCaseStatement CaseStatement()
	{
		AstCaseStatement ast = new AstCaseStatement();
		scanner.test("CaseStatement", RDP_T_case, CaseStatement_stop);
		ast.add(lastsym);
		getsym();
		ast.add(Expression());
		scanner.test("CaseStatement", RDP_T_58 /* : */, CaseStatement_stop);
		ast.add(lastsym);
		getsym();
		if (scanner.test("rdp_CaseStatement_1", rdp_CaseStatement_1_first, null))
		{
			while (true)
			{
				ast.add(Statement());
				if (!scanner.test("rdp_CaseStatement_1", rdp_CaseStatement_1_first, null))
				{
					break;
				}
			}
		}
		scanner.test("CaseStatement", CaseStatement_stop, CaseStatement_stop);
		return ast;
	}

	protected AstCompareExpression CompareExpression()
	{
		AstCompareExpression ast = new AstCompareExpression();
		ast.add(ShiftExpression());
		if (scanner.test("rdp_CompareExpression_1", rdp_CompareExpression_1_first, null))
		{
			while (true)
			{
				ast.add(CompareOp());
				ast.add(ShiftExpression());
				if (!scanner.test("rdp_CompareExpression_1", rdp_CompareExpression_1_first, null))
				{
					break;
				}
			}
		}
		scanner.test("CompareExpression", CompareExpression_stop, CompareExpression_stop);
		return ast;
	}

	protected AstCompareOp CompareOp()
	{
		AstCompareOp ast = new AstCompareOp();
		if (scanner.test("rdp_CompareOp_0", RDP_T_60 /* < */, null))
		{
			scanner.test("CompareOp", RDP_T_60 /* < */, CompareOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_CompareOp_1", RDP_T_6061 /* <= */, null))
		{
			scanner.test("CompareOp", RDP_T_6061 /* <= */, CompareOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_CompareOp_2", RDP_T_62 /* > */, null))
		{
			scanner.test("CompareOp", RDP_T_62 /* > */, CompareOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_CompareOp_3", RDP_T_6261 /* >= */, null))
		{
			scanner.test("CompareOp", RDP_T_6261 /* >= */, CompareOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_CompareOp_4", RDP_T_instanceof, null))
		{
			scanner.test("CompareOp", RDP_T_instanceof, CompareOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_CompareOp_5", RDP_T_in, null))
		{
			scanner.test("CompareOp", RDP_T_in, CompareOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else
		{
			scanner.test("CompareOp", CompareOp_first, CompareOp_stop);
		}
		scanner.test("CompareOp", CompareOp_stop, CompareOp_stop);
		return ast;
	}

	protected AstCompoundInitializer CompoundInitializer()
	{
		AstCompoundInitializer ast = new AstCompoundInitializer();
		scanner.test("rdp_CompoundInitializer_2", rdp_CompoundInitializer_2_first, CompoundInitializer_stop);
		if (scanner.test("rdp_CompoundInitializer_0", rdp_CompoundInitializer_0_first, null))
		{
			ast.add(String());
		}
		else if (scanner.test("rdp_CompoundInitializer_1", SCAN_P_ID, null))
		{
			scanner.test("CompoundInitializer", SCAN_P_ID, CompoundInitializer_stop);
			ast.add(lastsym);
			getsym();
		}
		else
		{
			scanner.test("rdp_CompoundInitializer_2", rdp_CompoundInitializer_2_first, CompoundInitializer_stop);
		}
		scanner.test("CompoundInitializer", RDP_T_58 /* : */, CompoundInitializer_stop);
		ast.add(lastsym);
		getsym();
		ast.add(Expression());
		scanner.test("CompoundInitializer", CompoundInitializer_stop, CompoundInitializer_stop);
		return ast;
	}

	protected AstConditionalExpression ConditionalExpression()
	{
		AstConditionalExpression ast = new AstConditionalExpression();
		ast.add(BoolOrExpression());
		if (scanner.test("rdp_ConditionalExpression_1", RDP_T_63 /* ? */, null))
		{
			scanner.test("ConditionalExpression", RDP_T_63 /* ? */, ConditionalExpression_stop);
			ast.add(lastsym);
			getsym();
			ast.add(Expression());
			scanner.test("ConditionalExpression", RDP_T_58 /* : */, ConditionalExpression_stop);
			ast.add(lastsym);
			getsym();
			ast.add(Expression());
		}
		scanner.test("ConditionalExpression", ConditionalExpression_stop, ConditionalExpression_stop);
		return ast;
	}

	protected AstDefaultStatement DefaultStatement()
	{
		AstDefaultStatement ast = new AstDefaultStatement();
		scanner.test("DefaultStatement", RDP_T_default, DefaultStatement_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("DefaultStatement", RDP_T_58 /* : */, DefaultStatement_stop);
		ast.add(lastsym);
		getsym();
		if (scanner.test("rdp_DefaultStatement_1", rdp_DefaultStatement_1_first, null))
		{
			while (true)
			{
				ast.add(Statement());
				if (!scanner.test("rdp_DefaultStatement_1", rdp_DefaultStatement_1_first, null))
				{
					break;
				}
			}
		}
		scanner.test("DefaultStatement", DefaultStatement_stop, DefaultStatement_stop);
		return ast;
	}

	protected AstDeobfuscator Deobfuscator()
	{
		AstDeobfuscator ast = new AstDeobfuscator();
		if (scanner.test("rdp_Deobfuscator_3", rdp_Deobfuscator_3_first, null))
		{
			while (true)
			{
				if (scanner.test("rdp_Deobfuscator_0", RDP_T_var, null))
				{
					ast.add(VarDeclaration());
				}
				else if (scanner.test("rdp_Deobfuscator_1", rdp_Deobfuscator_1_first, null))
				{
					ast.add(AssignmentExpression());
				}
				else if (scanner.test("rdp_Deobfuscator_2", RDP_T_59 /* ; */, null))
				{
					scanner.test("Deobfuscator", RDP_T_59 /* ; */, Deobfuscator_stop);
					ast.add(lastsym);
					getsym();
				}
				else
				{
					scanner.test("rdp_Deobfuscator_3", rdp_Deobfuscator_3_first, Deobfuscator_stop);
				}
				if (!scanner.test("rdp_Deobfuscator_3", rdp_Deobfuscator_3_first, null))
				{
					break;
				}
			}
		}
		scanner.test("Deobfuscator", Deobfuscator_stop, Deobfuscator_stop);
		return ast;
	}

	protected AstDoStatement DoStatement()
	{
		AstDoStatement ast = new AstDoStatement();
		scanner.test("DoStatement", RDP_T_do, DoStatement_stop);
		ast.add(lastsym);
		getsym();
		ast.add(Block());
		scanner.test("DoStatement", RDP_T_while, DoStatement_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("DoStatement", RDP_T_40 /* ( */, DoStatement_stop);
		ast.add(lastsym);
		getsym();
		ast.add(Expression());
		scanner.test("DoStatement", RDP_T_41 /* ) */, DoStatement_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("DoStatement", RDP_T_59 /* ; */, DoStatement_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("DoStatement", DoStatement_stop, DoStatement_stop);
		return ast;
	}

	protected AstDotExpression DotExpression()
	{
		AstDotExpression ast = new AstDotExpression();
		scanner.test("DotExpression", RDP_T_46 /* . */, DotExpression_stop);
		ast.add(lastsym);
		getsym();
		ast.add(Identifier());
		if (scanner.test("rdp_DotExpression_12", RDP_T_40 /* ( */, null))
		{
			scanner.test("DotExpression", RDP_T_40 /* ( */, DotExpression_stop);
			ast.add(lastsym);
			getsym();
			if (scanner.test("rdp_DotExpression_4", RDP_T_123 /* { */, null))
			{
				scanner.test("DotExpression", RDP_T_123 /* { */, DotExpression_stop);
				ast.add(lastsym);
				getsym();
				if (scanner.test("rdp_DotExpression_3", rdp_DotExpression_3_first, null))
				{
					ast.add(CompoundInitializer());
					if (scanner.test("rdp_DotExpression_1", RDP_T_44 /* , */, null))
					{
						while (true)
						{
							scanner.test("DotExpression", RDP_T_44 /* , */, DotExpression_stop);
							ast.add(lastsym);
							getsym();
							ast.add(CompoundInitializer());
							if (!scanner.test("rdp_DotExpression_1", RDP_T_44 /* , */, null))
							{
								break;
							}
						}
					}
				}
				scanner.test("DotExpression", RDP_T_125 /* } */, DotExpression_stop);
				ast.add(lastsym);
				getsym();
			}
			else if (scanner.test("rdp_DotExpression_9", rdp_DotExpression_9_first, null))
			{
				if (scanner.test("rdp_DotExpression_8", rdp_DotExpression_8_first, null))
				{
					ast.add(ConditionalExpression());
					if (scanner.test("rdp_DotExpression_6", RDP_T_44 /* , */, null))
					{
						while (true)
						{
							scanner.test("DotExpression", RDP_T_44 /* , */, DotExpression_stop);
							ast.add(lastsym);
							getsym();
							ast.add(ConditionalExpression());
							if (!scanner.test("rdp_DotExpression_6", RDP_T_44 /* , */, null))
							{
								break;
							}
						}
					}
				}
			}
			scanner.test("DotExpression", RDP_T_41 /* ) */, DotExpression_stop);
			ast.add(lastsym);
			getsym();
		}
		scanner.test("DotExpression", DotExpression_stop, DotExpression_stop);
		return ast;
	}

	protected AstElseStatement ElseStatement()
	{
		AstElseStatement ast = new AstElseStatement();
		scanner.test("ElseStatement", RDP_T_else, ElseStatement_stop);
		ast.add(lastsym);
		getsym();
		ast.add(BlockOrStatement());
		scanner.test("ElseStatement", ElseStatement_stop, ElseStatement_stop);
		return ast;
	}

	protected AstEmptyStatement EmptyStatement()
	{
		AstEmptyStatement ast = new AstEmptyStatement();
		scanner.test("EmptyStatement", RDP_T_59 /* ; */, EmptyStatement_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("EmptyStatement", EmptyStatement_stop, EmptyStatement_stop);
		return ast;
	}

	protected AstEqualsExpression EqualsExpression()
	{
		AstEqualsExpression ast = new AstEqualsExpression();
		ast.add(CompareExpression());
		if (scanner.test("rdp_EqualsExpression_1", rdp_EqualsExpression_1_first, null))
		{
			while (true)
			{
				ast.add(EqualsOp());
				ast.add(CompareExpression());
				if (!scanner.test("rdp_EqualsExpression_1", rdp_EqualsExpression_1_first, null))
				{
					break;
				}
			}
		}
		scanner.test("EqualsExpression", EqualsExpression_stop, EqualsExpression_stop);
		return ast;
	}

	protected AstEqualsOp EqualsOp()
	{
		AstEqualsOp ast = new AstEqualsOp();
		if (scanner.test("rdp_EqualsOp_0", RDP_T_6161 /* == */, null))
		{
			scanner.test("EqualsOp", RDP_T_6161 /* == */, EqualsOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_EqualsOp_1", RDP_T_3361 /* != */, null))
		{
			scanner.test("EqualsOp", RDP_T_3361 /* != */, EqualsOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_EqualsOp_2", RDP_T_616161 /* === */, null))
		{
			scanner.test("EqualsOp", RDP_T_616161 /* === */, EqualsOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_EqualsOp_3", RDP_T_336161 /* !== */, null))
		{
			scanner.test("EqualsOp", RDP_T_336161 /* !== */, EqualsOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else
		{
			scanner.test("EqualsOp", EqualsOp_first, EqualsOp_stop);
		}
		scanner.test("EqualsOp", EqualsOp_stop, EqualsOp_stop);
		return ast;
	}

	protected AstExpression Expression()
	{
		AstExpression ast = new AstExpression();
		if (scanner.test("rdp_Expression_0", rdp_Expression_0_first, null))
		{
			ast.add(ConditionalExpression());
		}
		else if (scanner.test("rdp_Expression_9", RDP_T_123 /* { */, null))
		{
			scanner.test("Expression", RDP_T_123 /* { */, Expression_stop);
			ast.add(lastsym);
			getsym();
			if (scanner.test("rdp_Expression_8", rdp_Expression_8_first, null))
			{
				ast.add(Expression());
				if (scanner.test("rdp_Expression_2", RDP_T_58 /* : */, null))
				{
					scanner.test("Expression", RDP_T_58 /* : */, Expression_stop);
					ast.add(lastsym);
					getsym();
					ast.add(Expression());
				}
				if (scanner.test("rdp_Expression_6", RDP_T_44 /* , */, null))
				{
					while (true)
					{
						scanner.test("Expression", RDP_T_44 /* , */, Expression_stop);
						ast.add(lastsym);
						getsym();
						ast.add(Expression());
						if (scanner.test("rdp_Expression_4", RDP_T_58 /* : */, null))
						{
							scanner.test("Expression", RDP_T_58 /* : */, Expression_stop);
							ast.add(lastsym);
							getsym();
							ast.add(Expression());
						}
						if (!scanner.test("rdp_Expression_6", RDP_T_44 /* , */, null))
						{
							break;
						}
					}
				}
			}
			scanner.test("Expression", RDP_T_125 /* } */, Expression_stop);
			ast.add(lastsym);
			getsym();
		}
		else
		{
			scanner.test("Expression", Expression_first, Expression_stop);
		}
		scanner.test("Expression", Expression_stop, Expression_stop);
		return ast;
	}

	protected AstForParameters ForParameters()
	{
		AstForParameters ast = new AstForParameters();
		if (scanner.test("rdp_ForParameters_4", RDP_T_59 /* ; */, null))
		{
			scanner.test("ForParameters", RDP_T_59 /* ; */, ForParameters_stop);
			ast.add(lastsym);
			getsym();
			if (scanner.test("rdp_ForParameters_1", rdp_ForParameters_1_first, null))
			{
				ast.add(Expression());
			}
			scanner.test("ForParameters", RDP_T_59 /* ; */, ForParameters_stop);
			ast.add(lastsym);
			getsym();
			if (scanner.test("rdp_ForParameters_3", rdp_ForParameters_3_first, null))
			{
				ast.add(Expression());
			}
		}
		else if (scanner.test("rdp_ForParameters_5", rdp_ForParameters_5_first, null))
		{
			ast.add(ForVarDeclaration());
		}
		else
		{
			scanner.test("ForParameters", ForParameters_first, ForParameters_stop);
		}
		scanner.test("ForParameters", ForParameters_stop, ForParameters_stop);
		return ast;
	}

	protected AstForStatement ForStatement()
	{
		AstForStatement ast = new AstForStatement();
		scanner.test("ForStatement", RDP_T_for, ForStatement_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("ForStatement", RDP_T_40 /* ( */, ForStatement_stop);
		ast.add(lastsym);
		getsym();
		ast.add(ForParameters());
		scanner.test("ForStatement", RDP_T_41 /* ) */, ForStatement_stop);
		ast.add(lastsym);
		getsym();
		ast.add(BlockOrStatement());
		scanner.test("ForStatement", ForStatement_stop, ForStatement_stop);
		return ast;
	}

	protected AstForVarDeclaration ForVarDeclaration()
	{
		AstForVarDeclaration ast = new AstForVarDeclaration();
		if (scanner.test("rdp_ForVarDeclaration_1", RDP_T_var, null))
		{
			scanner.test("ForVarDeclaration", RDP_T_var, ForVarDeclaration_stop);
			ast.add(lastsym);
			getsym();
		}
		scanner.test("ForVarDeclaration", SCAN_P_ID, ForVarDeclaration_stop);
		ast.add(lastsym);
		getsym();
		ast.add(ForVarDeclaration2());
		scanner.test("ForVarDeclaration", ForVarDeclaration_stop, ForVarDeclaration_stop);
		return ast;
	}

	protected AstForVarDeclaration2 ForVarDeclaration2()
	{
		AstForVarDeclaration2 ast = new AstForVarDeclaration2();
		if (scanner.test("rdp_ForVarDeclaration2_6", RDP_T_61 /* = */, null))
		{
			scanner.test("ForVarDeclaration2", RDP_T_61 /* = */, ForVarDeclaration2_stop);
			ast.add(lastsym);
			getsym();
			ast.add(Expression());
			if (scanner.test("rdp_ForVarDeclaration2_1", RDP_T_44 /* , */, null))
			{
				while (true)
				{
					scanner.test("ForVarDeclaration2", RDP_T_44 /* , */, ForVarDeclaration2_stop);
					ast.add(lastsym);
					getsym();
					scanner.test("ForVarDeclaration2", SCAN_P_ID, ForVarDeclaration2_stop);
					ast.add(lastsym);
					getsym();
					scanner.test("ForVarDeclaration2", RDP_T_61 /* = */, ForVarDeclaration2_stop);
					ast.add(lastsym);
					getsym();
					ast.add(Expression());
					if (!scanner.test("rdp_ForVarDeclaration2_1", RDP_T_44 /* , */, null))
					{
						break;
					}
				}
			}
			scanner.test("ForVarDeclaration2", RDP_T_59 /* ; */, ForVarDeclaration2_stop);
			ast.add(lastsym);
			getsym();
			if (scanner.test("rdp_ForVarDeclaration2_3", rdp_ForVarDeclaration2_3_first, null))
			{
				ast.add(Expression());
			}
			scanner.test("ForVarDeclaration2", RDP_T_59 /* ; */, ForVarDeclaration2_stop);
			ast.add(lastsym);
			getsym();
			if (scanner.test("rdp_ForVarDeclaration2_5", rdp_ForVarDeclaration2_5_first, null))
			{
				ast.add(AssignmentExpression());
			}
		}
		else if (scanner.test("rdp_ForVarDeclaration2_7", RDP_T_in, null))
		{
			scanner.test("ForVarDeclaration2", RDP_T_in, ForVarDeclaration2_stop);
			ast.add(lastsym);
			getsym();
			ast.add(Expression());
		}
		else
		{
			scanner.test("ForVarDeclaration2", ForVarDeclaration2_first, ForVarDeclaration2_stop);
		}
		scanner.test("ForVarDeclaration2", ForVarDeclaration2_stop, ForVarDeclaration2_stop);
		return ast;
	}

	protected AstFunction Function()
	{
		AstFunction ast = new AstFunction();
		scanner.test("Function", RDP_T_function, Function_stop);
		ast.add(lastsym);
		getsym();
		if (scanner.test("rdp_Function_1", SCAN_P_ID, null))
		{
			scanner.test("Function", SCAN_P_ID, Function_stop);
			ast.add(lastsym);
			getsym();
		}
		scanner.test("Function", RDP_T_40 /* ( */, Function_stop);
		ast.add(lastsym);
		getsym();
		if (scanner.test("rdp_Function_5", rdp_Function_5_first, null))
		{
			ast.add(Identifier());
			if (scanner.test("rdp_Function_3", RDP_T_44 /* , */, null))
			{
				while (true)
				{
					scanner.test("Function", RDP_T_44 /* , */, Function_stop);
					ast.add(lastsym);
					getsym();
					ast.add(Identifier());
					if (!scanner.test("rdp_Function_3", RDP_T_44 /* , */, null))
					{
						break;
					}
				}
			}
		}
		scanner.test("Function", RDP_T_41 /* ) */, Function_stop);
		ast.add(lastsym);
		getsym();
		ast.add(Block());
		scanner.test("Function", Function_stop, Function_stop);
		return ast;
	}

	protected AstIdentifier Identifier()
	{
		AstIdentifier ast = new AstIdentifier();
		scanner.test("rdp_Identifier_2", rdp_Identifier_2_first, Identifier_stop);
		if (scanner.test("rdp_Identifier_0", SCAN_P_ID, null))
		{
			scanner.test("Identifier", SCAN_P_ID, Identifier_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_Identifier_1", RDP_T_36 /* $ */, null))
		{
			scanner.test("Identifier", RDP_T_36 /* $ */, Identifier_stop);
			ast.add(lastsym);
			getsym();
		}
		else
		{
			scanner.test("rdp_Identifier_2", rdp_Identifier_2_first, Identifier_stop);
		}
		if (scanner.test("rdp_Identifier_5", rdp_Identifier_5_first, null))
		{
			while (true)
			{
				if (scanner.test("rdp_Identifier_3", RDP_T_36 /* $ */, null))
				{
					scanner.test("Identifier", RDP_T_36 /* $ */, Identifier_stop);
					ast.add(lastsym);
					getsym();
				}
				else if (scanner.test("rdp_Identifier_4", SCAN_P_ID, null))
				{
					scanner.test("Identifier", SCAN_P_ID, Identifier_stop);
					ast.add(lastsym);
					getsym();
				}
				else
				{
					scanner.test("rdp_Identifier_5", rdp_Identifier_5_first, Identifier_stop);
				}
				if (!scanner.test("rdp_Identifier_5", rdp_Identifier_5_first, null))
				{
					break;
				}
			}
		}
		scanner.test("Identifier", Identifier_stop, Identifier_stop);
		return ast;
	}

	protected AstIfStatement IfStatement()
	{
		AstIfStatement ast = new AstIfStatement();
		scanner.test("IfStatement", RDP_T_if, IfStatement_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("IfStatement", RDP_T_40 /* ( */, IfStatement_stop);
		ast.add(lastsym);
		getsym();
		ast.add(AssignmentExpression());
		if (scanner.test("rdp_IfStatement_1", RDP_T_44 /* , */, null))
		{
			while (true)
			{
				scanner.test("IfStatement", RDP_T_44 /* , */, IfStatement_stop);
				ast.add(lastsym);
				getsym();
				ast.add(AssignmentExpression());
				if (!scanner.test("rdp_IfStatement_1", RDP_T_44 /* , */, null))
				{
					break;
				}
			}
		}
		scanner.test("IfStatement", RDP_T_41 /* ) */, IfStatement_stop);
		ast.add(lastsym);
		getsym();
		ast.add(BlockOrStatement());
		scanner.test("IfStatement", IfStatement_stop, IfStatement_stop);
		return ast;
	}

	protected AstMultiplyExpression MultiplyExpression()
	{
		AstMultiplyExpression ast = new AstMultiplyExpression();
		ast.add(UnaryExpression());
		if (scanner.test("rdp_MultiplyExpression_1", rdp_MultiplyExpression_1_first, null))
		{
			while (true)
			{
				ast.add(MultiplyOp());
				ast.add(UnaryExpression());
				if (!scanner.test("rdp_MultiplyExpression_1", rdp_MultiplyExpression_1_first, null))
				{
					break;
				}
			}
		}
		scanner.test("MultiplyExpression", MultiplyExpression_stop, MultiplyExpression_stop);
		return ast;
	}

	protected AstMultiplyOp MultiplyOp()
	{
		AstMultiplyOp ast = new AstMultiplyOp();
		if (scanner.test("rdp_MultiplyOp_0", RDP_T_42 /* * */, null))
		{
			scanner.test("MultiplyOp", RDP_T_42 /* * */, MultiplyOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_MultiplyOp_1", RDP_T_47 /* / */, null))
		{
			scanner.test("MultiplyOp", RDP_T_47 /* / */, MultiplyOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_MultiplyOp_2", RDP_T_37 /* % */, null))
		{
			scanner.test("MultiplyOp", RDP_T_37 /* % */, MultiplyOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else
		{
			scanner.test("MultiplyOp", MultiplyOp_first, MultiplyOp_stop);
		}
		scanner.test("MultiplyOp", MultiplyOp_stop, MultiplyOp_stop);
		return ast;
	}

	protected AstPrimary Primary()
	{
		AstPrimary ast = new AstPrimary();
		scanner.test("rdp_Primary_11", rdp_Primary_11_first, Primary_stop);
		if (scanner.test("rdp_Primary_0", RDP_T_function, null))
		{
			ast.add(Function());
		}
		else if (scanner.test("rdp_Primary_1", rdp_Primary_1_first, null))
		{
			ast.add(Identifier());
		}
		else if (scanner.test("rdp_Primary_2", SCAN_P_INTEGER, null))
		{
			scanner.test("Primary", SCAN_P_INTEGER, Primary_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_Primary_3", SCAN_P_REAL, null))
		{
			scanner.test("Primary", SCAN_P_REAL, Primary_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_Primary_4", rdp_Primary_4_first, null))
		{
			ast.add(String());
		}
		else if (scanner.test("rdp_Primary_5", RDP_T_this, null))
		{
			scanner.test("Primary", RDP_T_this, Primary_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_Primary_10", RDP_T_91 /* [ */, null))
		{
			scanner.test("Primary", RDP_T_91 /* [ */, Primary_stop);
			ast.add(lastsym);
			getsym();
			if (scanner.test("rdp_Primary_9", rdp_Primary_9_first, null))
			{
				ast.add(Expression());
				if (scanner.test("rdp_Primary_7", RDP_T_44 /* , */, null))
				{
					while (true)
					{
						scanner.test("Primary", RDP_T_44 /* , */, Primary_stop);
						ast.add(lastsym);
						getsym();
						ast.add(Expression());
						if (!scanner.test("rdp_Primary_7", RDP_T_44 /* , */, null))
						{
							break;
						}
					}
				}
			}
			scanner.test("Primary", RDP_T_93 /* ] */, Primary_stop);
			ast.add(lastsym);
			getsym();
		}
		else
		{
			scanner.test("rdp_Primary_11", rdp_Primary_11_first, Primary_stop);
		}
		if (scanner.test("rdp_Primary_14", rdp_Primary_14_first, null))
		{
			if (scanner.test("rdp_Primary_12", RDP_T_4343 /* ++ */, null))
			{
				scanner.test("Primary", RDP_T_4343 /* ++ */, Primary_stop);
				ast.add(lastsym);
				getsym();
			}
			else if (scanner.test("rdp_Primary_13", RDP_T_4545 /* -- */, null))
			{
				scanner.test("Primary", RDP_T_4545 /* -- */, Primary_stop);
				ast.add(lastsym);
				getsym();
			}
			else
			{
				scanner.test("rdp_Primary_14", rdp_Primary_14_first, Primary_stop);
			}
		}
		scanner.test("Primary", Primary_stop, Primary_stop);
		return ast;
	}

	protected AstPrimaryExpression PrimaryExpression()
	{
		AstPrimaryExpression ast = new AstPrimaryExpression();
		if (scanner.test("rdp_PrimaryExpression_10", RDP_T_40 /* ( */, null))
		{
			scanner.test("PrimaryExpression", RDP_T_40 /* ( */, PrimaryExpression_stop);
			ast.add(lastsym);
			getsym();
			if (scanner.test("rdp_PrimaryExpression_3", rdp_PrimaryExpression_3_first, null))
			{
				ast.add(Expression());
				if (scanner.test("rdp_PrimaryExpression_1", RDP_T_44 /* , */, null))
				{
					while (true)
					{
						scanner.test("PrimaryExpression", RDP_T_44 /* , */, PrimaryExpression_stop);
						ast.add(lastsym);
						getsym();
						ast.add(Expression());
						if (!scanner.test("rdp_PrimaryExpression_1", RDP_T_44 /* , */, null))
						{
							break;
						}
					}
				}
			}
			if (scanner.test("rdp_PrimaryExpression_7", rdp_PrimaryExpression_7_first, null))
			{
				ast.add(AssignmentOp());
				ast.add(Expression());
				if (scanner.test("rdp_PrimaryExpression_5", RDP_T_44 /* , */, null))
				{
					while (true)
					{
						scanner.test("PrimaryExpression", RDP_T_44 /* , */, PrimaryExpression_stop);
						ast.add(lastsym);
						getsym();
						ast.add(AssignmentExpression());
						if (!scanner.test("rdp_PrimaryExpression_5", RDP_T_44 /* , */, null))
						{
							break;
						}
					}
				}
			}
			scanner.test("PrimaryExpression", RDP_T_41 /* ) */, PrimaryExpression_stop);
			ast.add(lastsym);
			getsym();
			if (scanner.test("rdp_PrimaryExpression_9", rdp_PrimaryExpression_9_first, null))
			{
				while (true)
				{
					ast.add(PrimaryOptions());
					if (!scanner.test("rdp_PrimaryExpression_9", rdp_PrimaryExpression_9_first, null))
					{
						break;
					}
				}
			}
		}
		else if (scanner.test("rdp_PrimaryExpression_13", rdp_PrimaryExpression_13_first, null))
		{
			ast.add(Primary());
			if (scanner.test("rdp_PrimaryExpression_12", rdp_PrimaryExpression_12_first, null))
			{
				while (true)
				{
					ast.add(PrimaryOptions());
					if (!scanner.test("rdp_PrimaryExpression_12", rdp_PrimaryExpression_12_first, null))
					{
						break;
					}
				}
			}
		}
		else
		{
			scanner.test("PrimaryExpression", PrimaryExpression_first, PrimaryExpression_stop);
		}
		scanner.test("PrimaryExpression", PrimaryExpression_stop, PrimaryExpression_stop);
		return ast;
	}

	protected AstPrimaryOptions PrimaryOptions()
	{
		AstPrimaryOptions ast = new AstPrimaryOptions();
		scanner.test("rdp_PrimaryOptions_13", rdp_PrimaryOptions_13_first, PrimaryOptions_stop);
		if (scanner.test("rdp_PrimaryOptions_2", RDP_T_91 /* [ */, null))
		{
			scanner.test("PrimaryOptions", RDP_T_91 /* [ */, PrimaryOptions_stop);
			ast.add(lastsym);
			getsym();
			ast.add(Expression());
			if (scanner.test("rdp_PrimaryOptions_1", RDP_T_44 /* , */, null))
			{
				while (true)
				{
					scanner.test("PrimaryOptions", RDP_T_44 /* , */, PrimaryOptions_stop);
					ast.add(lastsym);
					getsym();
					ast.add(Expression());
					if (!scanner.test("rdp_PrimaryOptions_1", RDP_T_44 /* , */, null))
					{
						break;
					}
				}
			}
			scanner.test("PrimaryOptions", RDP_T_93 /* ] */, PrimaryOptions_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_PrimaryOptions_11", RDP_T_40 /* ( */, null))
		{
			scanner.test("PrimaryOptions", RDP_T_40 /* ( */, PrimaryOptions_stop);
			ast.add(lastsym);
			getsym();
			if (scanner.test("rdp_PrimaryOptions_10", rdp_PrimaryOptions_10_first, null))
			{
				ast.add(Expression());
				if (scanner.test("rdp_PrimaryOptions_4", RDP_T_44 /* , */, null))
				{
					while (true)
					{
						scanner.test("PrimaryOptions", RDP_T_44 /* , */, PrimaryOptions_stop);
						ast.add(lastsym);
						getsym();
						ast.add(Expression());
						if (!scanner.test("rdp_PrimaryOptions_4", RDP_T_44 /* , */, null))
						{
							break;
						}
					}
				}
				if (scanner.test("rdp_PrimaryOptions_8", rdp_PrimaryOptions_8_first, null))
				{
					while (true)
					{
						ast.add(AssignmentOp());
						ast.add(Expression());
						if (scanner.test("rdp_PrimaryOptions_6", RDP_T_44 /* , */, null))
						{
							while (true)
							{
								scanner.test("PrimaryOptions", RDP_T_44 /* , */, PrimaryOptions_stop);
								ast.add(lastsym);
								getsym();
								ast.add(AssignmentExpression());
								if (!scanner.test("rdp_PrimaryOptions_6", RDP_T_44 /* , */, null))
								{
									break;
								}
							}
						}
						if (!scanner.test("rdp_PrimaryOptions_8", rdp_PrimaryOptions_8_first, null))
						{
							break;
						}
					}
				}
			}
			scanner.test("PrimaryOptions", RDP_T_41 /* ) */, PrimaryOptions_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_PrimaryOptions_12", RDP_T_46 /* . */, null))
		{
			ast.add(DotExpression());
		}
		else
		{
			scanner.test("rdp_PrimaryOptions_13", rdp_PrimaryOptions_13_first, PrimaryOptions_stop);
		}
		if (scanner.test("rdp_PrimaryOptions_18", rdp_PrimaryOptions_18_first, null))
		{
			while (true)
			{
				scanner.test("rdp_PrimaryOptions_16", rdp_PrimaryOptions_16_first, PrimaryOptions_stop);
				if (scanner.test("rdp_PrimaryOptions_14", RDP_T_4343 /* ++ */, null))
				{
					scanner.test("PrimaryOptions", RDP_T_4343 /* ++ */, PrimaryOptions_stop);
					ast.add(lastsym);
					getsym();
				}
				else if (scanner.test("rdp_PrimaryOptions_15", RDP_T_4545 /* -- */, null))
				{
					scanner.test("PrimaryOptions", RDP_T_4545 /* -- */, PrimaryOptions_stop);
					ast.add(lastsym);
					getsym();
				}
				else
				{
					scanner.test("rdp_PrimaryOptions_16", rdp_PrimaryOptions_16_first, PrimaryOptions_stop);
				}
				if (!scanner.test("rdp_PrimaryOptions_18", rdp_PrimaryOptions_18_first, null))
				{
					break;
				}
			}
		}
		scanner.test("PrimaryOptions", PrimaryOptions_stop, PrimaryOptions_stop);
		return ast;
	}

	protected AstReturnStatement ReturnStatement()
	{
		AstReturnStatement ast = new AstReturnStatement();
		scanner.test("ReturnStatement", RDP_T_return, ReturnStatement_stop);
		ast.add(lastsym);
		getsym();
		if (scanner.test("rdp_ReturnStatement_5", rdp_ReturnStatement_5_first, null))
		{
			ast.add(Expression());
			if (scanner.test("rdp_ReturnStatement_1", rdp_ReturnStatement_1_first, null))
			{
				ast.add(AssignmentOp());
				ast.add(Expression());
			}
			if (scanner.test("rdp_ReturnStatement_3", RDP_T_44 /* , */, null))
			{
				while (true)
				{
					scanner.test("ReturnStatement", RDP_T_44 /* , */, ReturnStatement_stop);
					ast.add(lastsym);
					getsym();
					ast.add(AssignmentExpression());
					if (!scanner.test("rdp_ReturnStatement_3", RDP_T_44 /* , */, null))
					{
						break;
					}
				}
			}
		}
		scanner.test("ReturnStatement", RDP_T_59 /* ; */, ReturnStatement_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("ReturnStatement", ReturnStatement_stop, ReturnStatement_stop);
		return ast;
	}

	protected AstShiftExpression ShiftExpression()
	{
		AstShiftExpression ast = new AstShiftExpression();
		ast.add(AddExpression());
		if (scanner.test("rdp_ShiftExpression_1", rdp_ShiftExpression_1_first, null))
		{
			while (true)
			{
				ast.add(ShiftOp());
				ast.add(AddExpression());
				if (!scanner.test("rdp_ShiftExpression_1", rdp_ShiftExpression_1_first, null))
				{
					break;
				}
			}
		}
		scanner.test("ShiftExpression", ShiftExpression_stop, ShiftExpression_stop);
		return ast;
	}

	protected AstShiftOp ShiftOp()
	{
		AstShiftOp ast = new AstShiftOp();
		if (scanner.test("rdp_ShiftOp_0", RDP_T_6060 /* << */, null))
		{
			scanner.test("ShiftOp", RDP_T_6060 /* << */, ShiftOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_ShiftOp_1", RDP_T_6262 /* >> */, null))
		{
			scanner.test("ShiftOp", RDP_T_6262 /* >> */, ShiftOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_ShiftOp_2", RDP_T_626262 /* >>> */, null))
		{
			scanner.test("ShiftOp", RDP_T_626262 /* >>> */, ShiftOp_stop);
			ast.add(lastsym);
			getsym();
		}
		else
		{
			scanner.test("ShiftOp", ShiftOp_first, ShiftOp_stop);
		}
		scanner.test("ShiftOp", ShiftOp_stop, ShiftOp_stop);
		return ast;
	}

	protected AstStatement Statement()
	{
		AstStatement ast = new AstStatement();
		if (scanner.test("rdp_Statement_0", RDP_T_var, null))
		{
			ast.add(VarStatement());
		}
		else if (scanner.test("rdp_Statement_1", RDP_T_if, null))
		{
			ast.add(IfStatement());
		}
		else if (scanner.test("rdp_Statement_2", RDP_T_else, null))
		{
			ast.add(ElseStatement());
		}
		else if (scanner.test("rdp_Statement_3", RDP_T_return, null))
		{
			ast.add(ReturnStatement());
		}
		else if (scanner.test("rdp_Statement_4", RDP_T_for, null))
		{
			ast.add(ForStatement());
		}
		else if (scanner.test("rdp_Statement_5", rdp_Statement_5_first, null))
		{
			ast.add(AssignmentStatement());
		}
		else if (scanner.test("rdp_Statement_6", RDP_T_switch, null))
		{
			ast.add(SwitchStatement());
		}
		else if (scanner.test("rdp_Statement_7", RDP_T_default, null))
		{
			ast.add(DefaultStatement());
		}
		else if (scanner.test("rdp_Statement_8", RDP_T_case, null))
		{
			ast.add(CaseStatement());
		}
		else if (scanner.test("rdp_Statement_9", RDP_T_break, null))
		{
			ast.add(BreakStatement());
		}
		else if (scanner.test("rdp_Statement_10", RDP_T_throw, null))
		{
			ast.add(ThrowStatement());
		}
		else if (scanner.test("rdp_Statement_11", RDP_T_59 /* ; */, null))
		{
			ast.add(EmptyStatement());
		}
		else if (scanner.test("rdp_Statement_12", RDP_T_try, null))
		{
			ast.add(TryStatement());
		}
		else if (scanner.test("rdp_Statement_13", RDP_T_while, null))
		{
			ast.add(WhileStatement());
		}
		else if (scanner.test("rdp_Statement_14", RDP_T_do, null))
		{
			ast.add(DoStatement());
		}
		else
		{
			scanner.test("Statement", Statement_first, Statement_stop);
		}
		scanner.test("Statement", Statement_stop, Statement_stop);
		return ast;
	}

	protected AstString String()
	{
		AstString ast = new AstString();
		if (scanner.test("rdp_String_0", RDP_T_34 /* " */, null))
		{
			scanner.test("String", RDP_T_34 /* " */, String_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_String_1", RDP_T_39 /* ' */, null))
		{
			scanner.test("String", RDP_T_39 /* ' */, String_stop);
			ast.add(lastsym);
			getsym();
		}
		else
		{
			scanner.test("String", String_first, String_stop);
		}
		scanner.test("String", String_stop, String_stop);
		return ast;
	}

	protected AstSwitchStatement SwitchStatement()
	{
		AstSwitchStatement ast = new AstSwitchStatement();
		scanner.test("SwitchStatement", RDP_T_switch, SwitchStatement_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("SwitchStatement", RDP_T_40 /* ( */, SwitchStatement_stop);
		ast.add(lastsym);
		getsym();
		ast.add(Expression());
		scanner.test("SwitchStatement", RDP_T_41 /* ) */, SwitchStatement_stop);
		ast.add(lastsym);
		getsym();
		ast.add(Block());
		scanner.test("SwitchStatement", SwitchStatement_stop, SwitchStatement_stop);
		return ast;
	}

	protected AstThrowStatement ThrowStatement()
	{
		AstThrowStatement ast = new AstThrowStatement();
		scanner.test("ThrowStatement", RDP_T_throw, ThrowStatement_stop);
		ast.add(lastsym);
		getsym();
		if (scanner.test("rdp_ThrowStatement_1", rdp_ThrowStatement_1_first, null))
		{
			ast.add(Expression());
		}
		scanner.test("ThrowStatement", RDP_T_59 /* ; */, ThrowStatement_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("ThrowStatement", ThrowStatement_stop, ThrowStatement_stop);
		return ast;
	}

	protected AstTryStatement TryStatement()
	{
		AstTryStatement ast = new AstTryStatement();
		scanner.test("TryStatement", RDP_T_try, TryStatement_stop);
		ast.add(lastsym);
		getsym();
		ast.add(Block());
		scanner.test("TryStatement", RDP_T_catch, TryStatement_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("TryStatement", RDP_T_40 /* ( */, TryStatement_stop);
		ast.add(lastsym);
		getsym();
		if (scanner.test("rdp_TryStatement_1", rdp_TryStatement_1_first, null))
		{
			ast.add(Identifier());
		}
		scanner.test("TryStatement", RDP_T_41 /* ) */, TryStatement_stop);
		ast.add(lastsym);
		getsym();
		ast.add(Block());
		if (scanner.test("rdp_TryStatement_3", RDP_T_finally, null))
		{
			scanner.test("TryStatement", RDP_T_finally, TryStatement_stop);
			ast.add(lastsym);
			getsym();
			ast.add(Block());
		}
		scanner.test("TryStatement", TryStatement_stop, TryStatement_stop);
		return ast;
	}

	protected AstUnaryExpression UnaryExpression()
	{
		AstUnaryExpression ast = new AstUnaryExpression();
		if (scanner.test("rdp_UnaryExpression_0", rdp_UnaryExpression_0_first, null))
		{
			ast.add(PrimaryExpression());
		}
		else if (scanner.test("rdp_UnaryExpression_1", RDP_T_43 /* + */, null))
		{
			scanner.test("UnaryExpression", RDP_T_43 /* + */, UnaryExpression_stop);
			ast.add(lastsym);
			getsym();
			ast.add(PrimaryExpression());
		}
		else if (scanner.test("rdp_UnaryExpression_2", RDP_T_45 /* - */, null))
		{
			scanner.test("UnaryExpression", RDP_T_45 /* - */, UnaryExpression_stop);
			ast.add(lastsym);
			getsym();
			ast.add(PrimaryExpression());
		}
		else if (scanner.test("rdp_UnaryExpression_3", RDP_T_126 /* ~ */, null))
		{
			scanner.test("UnaryExpression", RDP_T_126 /* ~ */, UnaryExpression_stop);
			ast.add(lastsym);
			getsym();
			ast.add(PrimaryExpression());
		}
		else if (scanner.test("rdp_UnaryExpression_4", RDP_T_33 /* ! */, null))
		{
			scanner.test("UnaryExpression", RDP_T_33 /* ! */, UnaryExpression_stop);
			ast.add(lastsym);
			getsym();
			ast.add(UnaryExpression());
		}
		else if (scanner.test("rdp_UnaryExpression_5", RDP_T_typeof, null))
		{
			scanner.test("UnaryExpression", RDP_T_typeof, UnaryExpression_stop);
			ast.add(lastsym);
			getsym();
			ast.add(PrimaryExpression());
		}
		else if (scanner.test("rdp_UnaryExpression_6", RDP_T_new, null))
		{
			scanner.test("UnaryExpression", RDP_T_new, UnaryExpression_stop);
			ast.add(lastsym);
			getsym();
			ast.add(PrimaryExpression());
		}
		else if (scanner.test("rdp_UnaryExpression_7", RDP_T_delete, null))
		{
			scanner.test("UnaryExpression", RDP_T_delete, UnaryExpression_stop);
			ast.add(lastsym);
			getsym();
			ast.add(PrimaryExpression());
		}
		else if (scanner.test("rdp_UnaryExpression_8", RDP_T_4343 /* ++ */, null))
		{
			scanner.test("UnaryExpression", RDP_T_4343 /* ++ */, UnaryExpression_stop);
			ast.add(lastsym);
			getsym();
			ast.add(PrimaryExpression());
		}
		else if (scanner.test("rdp_UnaryExpression_9", RDP_T_4545 /* -- */, null))
		{
			scanner.test("UnaryExpression", RDP_T_4545 /* -- */, UnaryExpression_stop);
			ast.add(lastsym);
			getsym();
			ast.add(PrimaryExpression());
		}
		else
		{
			scanner.test("UnaryExpression", UnaryExpression_first, UnaryExpression_stop);
		}
		scanner.test("UnaryExpression", UnaryExpression_stop, UnaryExpression_stop);
		return ast;
	}

	protected AstVarDeclaration VarDeclaration()
	{
		AstVarDeclaration ast = new AstVarDeclaration();
		scanner.test("VarDeclaration", RDP_T_var, VarDeclaration_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("VarDeclaration", SCAN_P_ID, VarDeclaration_stop);
		ast.add(lastsym);
		getsym();
		if (scanner.test("rdp_VarDeclaration_1", RDP_T_61 /* = */, null))
		{
			scanner.test("VarDeclaration", RDP_T_61 /* = */, VarDeclaration_stop);
			ast.add(lastsym);
			getsym();
			ast.add(VarInitializer());
		}
		if (scanner.test("rdp_VarDeclaration_5", RDP_T_44 /* , */, null))
		{
			while (true)
			{
				scanner.test("VarDeclaration", RDP_T_44 /* , */, VarDeclaration_stop);
				ast.add(lastsym);
				getsym();
				scanner.test("VarDeclaration", SCAN_P_ID, VarDeclaration_stop);
				ast.add(lastsym);
				getsym();
				if (scanner.test("rdp_VarDeclaration_3", RDP_T_61 /* = */, null))
				{
					scanner.test("VarDeclaration", RDP_T_61 /* = */, VarDeclaration_stop);
					ast.add(lastsym);
					getsym();
					ast.add(VarInitializer());
				}
				if (!scanner.test("rdp_VarDeclaration_5", RDP_T_44 /* , */, null))
				{
					break;
				}
			}
		}
		scanner.test("VarDeclaration", RDP_T_59 /* ; */, VarDeclaration_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("VarDeclaration", VarDeclaration_stop, VarDeclaration_stop);
		return ast;
	}

	protected AstVarInitializer VarInitializer()
	{
		AstVarInitializer ast = new AstVarInitializer();
		if (scanner.test("rdp_VarInitializer_2", RDP_T_61 /* = */, null))
		{
			scanner.test("VarInitializer", RDP_T_61 /* = */, VarInitializer_stop);
			ast.add(lastsym);
			getsym();
			scanner.test("VarInitializer", RDP_T_123 /* { */, VarInitializer_stop);
			ast.add(lastsym);
			getsym();
			while (true)
			{
				scanner.test("rdp_VarInitializer_1", rdp_VarInitializer_1_first, VarInitializer_stop);
				ast.add(CompoundInitializer());
				if (lastsym != RDP_T_44 /* , */)
				{
					break;
				}
				ast.add(lastsym);
				getsym();
			}
			scanner.test("VarInitializer", RDP_T_125 /* } */, VarInitializer_stop);
			ast.add(lastsym);
			getsym();
		}
		else if (scanner.test("rdp_VarInitializer_3", rdp_VarInitializer_3_first, null))
		{
			ast.add(Expression());
		}
		else
		{
			scanner.test("VarInitializer", VarInitializer_first, VarInitializer_stop);
		}
		scanner.test("VarInitializer", VarInitializer_stop, VarInitializer_stop);
		return ast;
	}

	protected AstVarStatement VarStatement()
	{
		AstVarStatement ast = new AstVarStatement();
		scanner.test("VarStatement", RDP_T_var, VarStatement_stop);
		ast.add(lastsym);
		getsym();
		ast.add(Identifier());
		if (scanner.test("rdp_VarStatement_1", RDP_T_61 /* = */, null))
		{
			while (true)
			{
				scanner.test("VarStatement", RDP_T_61 /* = */, VarStatement_stop);
				ast.add(lastsym);
				getsym();
				ast.add(Expression());
				if (!scanner.test("rdp_VarStatement_1", RDP_T_61 /* = */, null))
				{
					break;
				}
			}
		}
		if (scanner.test("rdp_VarStatement_5", RDP_T_44 /* , */, null))
		{
			while (true)
			{
				scanner.test("VarStatement", RDP_T_44 /* , */, VarStatement_stop);
				ast.add(lastsym);
				getsym();
				ast.add(Identifier());
				if (scanner.test("rdp_VarStatement_3", RDP_T_61 /* = */, null))
				{
					while (true)
					{
						scanner.test("VarStatement", RDP_T_61 /* = */, VarStatement_stop);
						ast.add(lastsym);
						getsym();
						ast.add(Expression());
						if (!scanner.test("rdp_VarStatement_3", RDP_T_61 /* = */, null))
						{
							break;
						}
					}
				}
				if (!scanner.test("rdp_VarStatement_5", RDP_T_44 /* , */, null))
				{
					break;
				}
			}
		}
		scanner.test("VarStatement", RDP_T_59 /* ; */, VarStatement_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("VarStatement", VarStatement_stop, VarStatement_stop);
		return ast;
	}

	protected AstWhileStatement WhileStatement()
	{
		AstWhileStatement ast = new AstWhileStatement();
		scanner.test("WhileStatement", RDP_T_while, WhileStatement_stop);
		ast.add(lastsym);
		getsym();
		scanner.test("WhileStatement", RDP_T_40 /* ( */, WhileStatement_stop);
		ast.add(lastsym);
		getsym();
		ast.add(AssignmentExpression());
		scanner.test("WhileStatement", RDP_T_41 /* ) */, WhileStatement_stop);
		ast.add(lastsym);
		getsym();
		ast.add(BlockOrStatement());
		scanner.test("WhileStatement", WhileStatement_stop, WhileStatement_stop);
		return ast;
	}
}
