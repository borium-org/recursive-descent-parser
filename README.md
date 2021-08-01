# Recursive Descent Parser (Generator)
RDP implementation in Java, with C and Java output backends. Output is a compile-and-execute
code in Java or C that implements a LL(1) parser for a BNF-described input language.


# RDP History
RDP was developed by Adrian Johnstone and Elizabeth Scott around 1994-2000 and was placed
into public domain. Latest version was 1.6 but this code is based on version 1.5 with the
parser grammar file  version 1.65. You can get the original 1.5 archive from
[a link](http://www.cs.hs-rm.de/~weber/rdp). Sources and some documentation are also stored
in the /rdp folder in this repository.


# Why RDP?
RDP output code structure closely follows the input language grammar without any extra
decorators or semantic actions. For example, compare ANTLR4 grammar file C.g4 and output
file CParser.java, and then compare RDPJ grammar file cotg.bnf and CotgCompiler.java from
/examples folder. cotg.bnf is a close approximation of JavaScript and the CotgCompiler.java
implements parser for that version of Javascript. I needed the parser mainly to construct
AST from the JavaScript input so that I could transform and optimize it, and RDP was a much
better fit than other parser generators for that task.


# Why Java port?
The original RDP was written in C. Debugging it in 2016 on a Windows PC is somewhat challenging.
Visual Studio 6 is still a decent tool, but 'edit and continue' feature is cumbersome. Later
versions of Visual Studio are slow. I never tried to debug anything in Eclipse/CDT so I don't
know how good or bad that combination is. However, debugging Java in Eclipse is quite easy,
source in Java looks great, compared to many other curly brace languages, so with all these
features combined it was a natural choice to port the code to Java. The code structure did not
change, the only non-portable feature of original RDP was pointer math over a large string
table that was implemented on a character array. Character array is still there, but instead
of pointers in that array the new code uses indices into that array.


# C Output Verification
The original RDP version was self-replicating. Given the input from rdp.bnf file RDP could
reproduce its own parser source code. The original port was verified by feeding rdp.bnf into
Java version of RDP and the output matched rdp.c/rdp.h that were produced by original RDP.
Error checking and recovery correctness was tested to a lesser extent, assuming that if
correct input produces correct output then incorrect input should produce same errors in both
RDP versions.


# Java Output Verification
Since output closely matches input grammar, and C and Java outputs are similar, all verification
was done using side-by-side code review of bnf input, and C and Java output. There are no
guarantees that the code is perfect, anything could have happened during C-to-Java conversion.
If you find an issue with Java output, first verify the output using original C version of RDP,
then check out C output mode of this port, and last, let's check out why Java output messes up
where others didn't. Errors during second step would indicate porting issues, third step would
indicate Java output issues.


# Sample Application
The first test application for this RDPJ (RDP for Java) was to deobfuscate Crown of the Gods
MMORTS client Javascript code to see if it is even practical to write userscripts to make the
game playable (spoiler alert: it isn't). So, the BNF file was created to describe JavaScript
grammar. In some places I had to take shortcuts (regular expression strings delimited by '/'
that I had to replace with "" strings manually, my simplified scanner couldn't process strings
starting with 'http://', there were no missing semicolons but there were extra commas where
JavaScript didn't care but LL(1) parser had serious issues with them.  After these manual tweaks
I had a source file that was possible to process using the RDP-generated parser.

I set up two Java projects in same Eclipse workspace: RDPJ and CotgDeob. RDPJ had two run
configurations, one for parser-only output, another for creating the AST out of input file.

## Command line arguments for launch configurations:

-E Native RDP option to add rule names to error messages, easier to see which rule caused the error

-p Optional, if present the output parser only checks the syntax without building AST object tree

-jCotg This prefix will be used for generated compiler/scanner/keyword classes

-J../CotgDeob The location for output files, /src/ and 'prefix in lower case' will be added, same
prefix will be used as a package name

../CotgDeob/cotg.bnf This is the BNF file for JavaScript
