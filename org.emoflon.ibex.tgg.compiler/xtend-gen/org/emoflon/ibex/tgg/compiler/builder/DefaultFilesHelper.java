package org.emoflon.ibex.tgg.compiler.builder;

import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class DefaultFilesHelper {
  private static String concatAndFormatMetamodelNameList(final List<String> metamodelNames) {
    final StringBuilder sb = new StringBuilder();
    if (((metamodelNames == null) || (metamodelNames.size() == 0))) {
      return sb.toString();
    }
    for (final String name : metamodelNames) {
      {
        sb.append(name);
        sb.append(System.lineSeparator());
      }
    }
    return sb.toString();
  }

  public static String generateDefaultSchema(final String projectName, final List<String> importURIs, final List<String> sourceMetamodels, final List<String> targetMetamodels) {
    String _lineSeparator = System.lineSeparator();
    String importSection = ("// Add imports here" + _lineSeparator);
    if (((importURIs != null) && (importURIs.size() > 0))) {
      final StringBuilder sb = new StringBuilder();
      for (final String modelURI : importURIs) {
        {
          sb.append("#import \"");
          sb.append(modelURI);
          sb.append("\"");
          sb.append(System.lineSeparator());
        }
      }
      importSection = sb.toString();
    }
    final String sourceSection = DefaultFilesHelper.concatAndFormatMetamodelNameList(sourceMetamodels);
    final String targetSection = DefaultFilesHelper.concatAndFormatMetamodelNameList(targetMetamodels);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(importSection);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#schema ");
    _builder.append(projectName);
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("#source {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(sourceSection, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#target { ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(targetSection, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("} ");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#correspondence {");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#attributeConditions {");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }

  public static String generateDefaultRule(final String schema, final String ruleName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#using ");
    _builder.append(schema);
    _builder.append(".*");
    _builder.newLineIfNotEmpty();
    _builder.append("#using AttrCondDefLibrary.*");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#rule ");
    _builder.append(ruleName);
    _builder.append(" #with ");
    _builder.append(schema);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#source { ");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#target {");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#correspondence {");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#attributeConditions {");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }

  public static String generateDefaultAttrCondDefLibrary() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#library AttrCondDefLibrary {");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics:  a:EString == b:EString");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("eq_string(a: EString, b: EString) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B], [B F], [F B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen:  [B B], [B F], [F B], [F F]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics:  a:EInt == b:EInt");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("eq_int(a: EInt, b: EInt) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B], [B F], [F B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen:  [B B], [B F], [F B], [F F]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics:  a:EFloat == b:EFloat");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("eq_float(a: EFloat, b: EFloat) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B], [B F], [F B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen:  [B B], [B F], [F B], [F F]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics:  a:EDouble == b:EDouble");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("eq_double(a: EDouble, b: EDouble) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B], [B F], [F B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen:  [B B], [B F], [F B], [F F]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics:  a:ELong == b:ELong");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("eq_long(a: ELong, b: ELong) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B], [B F], [F B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen:  [B B], [B F], [F B], [F F]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics:  a:EChar == b:EChar");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("eq_char(a: EChar, b: EChar) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#sync: [B B], [B F], [F B]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#gen:  [B B], [B F], [F B], [F F]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics:  a:EBoolean == b:EBoolean");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("eq_boolean(a: EBoolean, b: EBoolean) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B], [B F], [F B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen:  [B B], [B F], [F B], [F F]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics: Prefix + Word = Result (where + is string concatenation)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("addPrefix(prefix:EString, word:EString, result:EString) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B B], [B B F], [B F B], [F B B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen: [B B B], [B B F], [B F B], [F B B], [B F F], [F B F]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics: Suffix + Word = Result (where + is string concatenation)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("addSuffix(suffix:EString, word:EString, result:EString) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B B], [B B F], [B F B], [F B B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen: [B B B], [B B F], [B F B], [F B B], [B F F], [F F F], [F B F]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics: LeftWord + Separator + RightWord = Result (where + is string concatenation)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Note:  Separator should be occur only once in Result");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("concat(separator:EString, leftWord:EString, rightWord:EString, result:EString) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B B B], [B B B F], [B B F B], [B F F B], [B F B B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen: [B B B B] , [B B B F], [B B F B], [B F F B], [B F B B], [B F B F], [B B F F], [B F F F]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics: VariableString is set to DefaultString if it is free (FB).");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("//            If it already has a value (BB) then nothing is done and the condition is still satisfied.");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("//            The case (_F) does not make sense for #sync as this should be a fixed default string.");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("setDefaultString(variableString:EString, defaultString:EString) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B], [F B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen: [B B], [F B], [F F]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics: VariableNumber is set to DefaultNumber if it is free (FB).");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("//            If it already has a value (BB) then nothing is done and the condition is still satisfied.");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("//            The case (_F) does not make sense for #sync as this should be a fixed default number.");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("setDefaultNumber(variableNumber:EDouble, defaultNumber:EDouble) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B], [F B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen: [B B], [F B], [F F]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics:  new Double(stringValue) == doubleValue (where == is equality for doubles)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("stringToDouble(stringValue:EString, doubleValue:EDouble) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B], [B F], [F B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen: [B B], [B F], [F B], [F F]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics:  new Double(stringValue) == intValue (where == is equality for ints)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("stringToInt(stringValue:EString, intValue:EInt) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B], [B F], [F B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen: [B B], [B F], [F B], [F F]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics:  operand1 * operand2 == result");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("multiply(operand1:EDouble, operand2:EDouble, result:EDouble) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B B], [B B F], [B F B], [F B B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen: [B B B], [B B F], [B F B], [F B B]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics: Numerator / Denominator == Result (/ is division for doubles)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("divide(numerator:EDouble, denominator:EDouble, result:EDouble) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B B], [B B F], [B F B], [F B B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen: [B B B], [B B F], [B F B], [F B B]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics: summand1 + summand2 == result (where + is addition for Numbers)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("add(summand1:EDouble, summand2:EDouble, result:EDouble) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B B], [B B F], [B F B], [F B B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen: [B B B], [B B F], [B F B], [F B B], [F F B], [F B F], [B F F]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics: minuend - subtrahend == result");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("sub(minuend:EDouble, subtrahend:EDouble, result:EDouble) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B B], [B B F], [B F B], [F B B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen: [B B B], [B B F], [B F B], [F B B], [F F B], [B F F], [F B F], [F F F]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics: max == max(a, b)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("max(a:EDouble, b:EDouble, max:EDouble) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B B], [B B F], [B F B], [F B B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen: [B B B], [B B F], [B F B], [F B B]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics: a <= b");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Note: For FB, BF, and FF, both a and b are set to the same value.");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("smallerOrEqual(a:EDouble, b:EDouble) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B B], [B F], [F B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen: [B B], [B F], [F B], [F F]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Semantics: Variable a is set to random string.");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// If it already has a value (B) then nothing is done and the condition is still satisfied.");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("setRandomString(a:EString) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#sync: [B]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#gen: [F], [B]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
}
