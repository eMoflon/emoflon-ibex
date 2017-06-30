package org.emoflon.ibex.tgg.compiler.defaults;

import java.util.Collection;
import language.csp.definition.TGGAttributeConstraintDefinition;
import language.csp.definition.TGGAttributeConstraintParameterDefinition;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.emoflon.ibex.tgg.compiler.defaults.UserAttrCondHelper;

@SuppressWarnings("all")
public class DefaultFilesGenerator {
  public static String generateUserRuntimeAttrCondFactory(final Collection<String> userDefConstraints) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package org.emoflon.ibex.tgg.operational.csp.constraints.factories;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import java.util.Collection;");
    _builder.newLine();
    _builder.append("import java.util.HashMap;");
    _builder.newLine();
    _builder.append("import java.util.HashSet;");
    _builder.newLine();
    _builder.append("import java.util.Map;");
    _builder.newLine();
    _builder.append("import java.util.function.Supplier;");
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;");
    _builder.newLine();
    _builder.newLine();
    {
      for(final String constraint : userDefConstraints) {
        _builder.append("import org.emoflon.ibex.tgg.operational.csp.constraints.custom.");
        String _fileName = UserAttrCondHelper.getFileName(constraint);
        _builder.append(_fileName, "");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("public class UserDefinedRuntimeTGGAttrConstraintFactory extends RuntimeTGGAttrConstraintFactory {");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private Collection<String> constraints; ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private Map<String, Supplier<RuntimeTGGAttributeConstraint>> creators;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public UserDefinedRuntimeTGGAttrConstraintFactory() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("initialize();");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private void initialize() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("creators = new HashMap<>();");
    _builder.newLine();
    {
      for(final String constraint_1 : userDefConstraints) {
        _builder.append("\t\t");
        _builder.append("creators.put(\"");
        _builder.append(constraint_1, "\t\t");
        _builder.append("\", () -> new ");
        String _fileName_1 = UserAttrCondHelper.getFileName(constraint_1);
        _builder.append(_fileName_1, "\t\t");
        _builder.append("());");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("constraints = new HashSet<String>();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("constraints.addAll(creators.keySet());");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public RuntimeTGGAttributeConstraint createRuntimeTGGAttributeConstraint(String name) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("Supplier<RuntimeTGGAttributeConstraint> creator = creators.get(name);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("if(creator == null)");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("throw new RuntimeException(\"CSP not implemented\");");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return creator.get();");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public boolean containsRuntimeTGGAttributeConstraint(String name) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return constraints.contains(name);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  public static String generateUserAttrCondDefStub(final TGGAttributeConstraintDefinition tacd) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package org.emoflon.ibex.tgg.operational.csp.constraints.custom;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;");
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    String _name = tacd.getName();
    String _fileName = UserAttrCondHelper.getFileName(_name);
    _builder.append(_fileName, "");
    _builder.append(" extends RuntimeTGGAttributeConstraint");
    _builder.newLineIfNotEmpty();
    _builder.append("{");
    _builder.newLine();
    _builder.newLine();
    _builder.append("   ");
    _builder.append("/**");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("* Constraint ");
    String _name_1 = tacd.getName();
    _builder.append(_name_1, "    ");
    _builder.append("(");
    String _parameterString = UserAttrCondHelper.getParameterString(tacd);
    _builder.append(_parameterString, "    ");
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("* ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("* @see TGGLanguage.csp.impl.ConstraintImpl#solve()");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void solve() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("if (variables.size() != ");
    EList<TGGAttributeConstraintParameterDefinition> _parameterDefinitions = tacd.getParameterDefinitions();
    int _size = _parameterDefinitions.size();
    _builder.append(_size, "\t\t");
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("throw new RuntimeException(\"The CSP -");
    String _name_2 = tacd.getName();
    String _upperCase = _name_2.toUpperCase();
    _builder.append(_upperCase, "\t\t\t");
    _builder.append("- needs exactly ");
    EList<TGGAttributeConstraintParameterDefinition> _parameterDefinitions_1 = tacd.getParameterDefinitions();
    int _size_1 = _parameterDefinitions_1.size();
    _builder.append(_size_1, "\t\t\t");
    _builder.append(" variables\");");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<TGGAttributeConstraintParameterDefinition> _parameterDefinitions_2 = tacd.getParameterDefinitions();
      for(final TGGAttributeConstraintParameterDefinition param : _parameterDefinitions_2) {
        _builder.append("\t\t");
        _builder.append("RuntimeTGGAttributeConstraintVariable v");
        EList<TGGAttributeConstraintParameterDefinition> _parameterDefinitions_3 = tacd.getParameterDefinitions();
        int _indexOf = _parameterDefinitions_3.indexOf(param);
        _builder.append(_indexOf, "\t\t");
        _builder.append(" = variables.get(");
        EList<TGGAttributeConstraintParameterDefinition> _parameterDefinitions_4 = tacd.getParameterDefinitions();
        int _indexOf_1 = _parameterDefinitions_4.indexOf(param);
        _builder.append(_indexOf_1, "\t\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.append("String bindingStates = getBindingStates(");
    String _parameterString_1 = UserAttrCondHelper.getParameterString(tacd);
    _builder.append(_parameterString_1, "\t\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t  \t");
    _builder.append("switch(bindingStates) {");
    _builder.newLine();
    {
      Collection<String> _adorments = UserAttrCondHelper.getAdorments(tacd);
      for(final String adornment : _adorments) {
        _builder.append("\t  \t\t");
        _builder.append("case \"");
        _builder.append(adornment, "\t  \t\t");
        _builder.append("\": ");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t  \t\t");
    _builder.append("default:  throw new UnsupportedOperationException(\"This case in the constraint has not been implemented yet: \" + bindingStates);");
    _builder.newLine();
    _builder.append("\t  \t\t \t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t  \t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    return _builder.toString();
  }
  
  public static String generateBasicStructure(final String additionalImports, final String fileName, final String strategy, final String engine, final String projectName, final String setUpRoutine) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package org.emoflon.ibex.tgg.run;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import java.io.IOException;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import org.apache.log4j.BasicConfigurator;");
    _builder.newLine();
    _builder.append(additionalImports, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public class ");
    _builder.append(fileName, "");
    _builder.append(" extends ");
    _builder.append(strategy, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    _builder.append(fileName, "\t");
    _builder.append("(String projectName, String workspacePath, boolean flatten, boolean debug) throws IOException {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("super(projectName, workspacePath, flatten, debug);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("registerPatternMatchingEngine(new ");
    _builder.append(engine, "\t\t");
    _builder.append("());");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static void main(String[] args) throws IOException {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("BasicConfigurator.configure();");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append(setUpRoutine, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    CharSequence _generateMetamodelRegistration = DefaultFilesGenerator.generateMetamodelRegistration();
    _builder.append(_generateMetamodelRegistration, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  public static String generateModelGenFile(final String projectName, final String fileName, final String engine, final String additionalImports) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGEN;");
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGENStopCriterion;");
    _builder.newLine();
    _builder.append(additionalImports, "");
    _builder.newLineIfNotEmpty();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append(fileName, "");
    _builder_1.append(" generator = new ");
    _builder_1.append(fileName, "");
    _builder_1.append("(\"");
    _builder_1.append(projectName, "");
    _builder_1.append("\", \"./../\", false, false);");
    _builder_1.newLineIfNotEmpty();
    _builder_1.newLine();
    _builder_1.append("MODELGENStopCriterion stop = new MODELGENStopCriterion(generator.tgg);");
    _builder_1.newLine();
    _builder_1.append("stop.setTimeOutInMS(1000);");
    _builder_1.newLine();
    _builder_1.append("generator.setStopCriterion(stop);");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Starting MODELGEN\");");
    _builder_1.newLine();
    _builder_1.append("long tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("generator.run();");
    _builder_1.newLine();
    _builder_1.append("long toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Completed MODELGEN in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("generator.saveModels();");
    _builder_1.newLine();
    _builder_1.append("generator.terminate();");
    _builder_1.newLine();
    return DefaultFilesGenerator.generateBasicStructure(_builder.toString(), fileName, 
      "MODELGEN", engine, projectName, _builder_1.toString());
  }
  
  public static String generateSyncAppFile(final String projectName, final String fileName, final String engine, final String additionalImports) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;");
    _builder.newLine();
    _builder.append(additionalImports, "");
    _builder.newLineIfNotEmpty();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append(fileName, "");
    _builder_1.append(" sync = new ");
    _builder_1.append(fileName, "");
    _builder_1.append("(\"");
    _builder_1.append(projectName, "");
    _builder_1.append("\", \"./../\", false, false);");
    _builder_1.newLineIfNotEmpty();
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Starting SYNC\");");
    _builder_1.newLine();
    _builder_1.append("long tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("sync.forward();");
    _builder_1.newLine();
    _builder_1.append("long toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Completed SYNC in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("sync.saveModels();");
    _builder_1.newLine();
    _builder_1.append("sync.terminate();");
    _builder_1.newLine();
    return DefaultFilesGenerator.generateBasicStructure(_builder.toString(), fileName, 
      "SYNC", engine, projectName, _builder_1.toString());
  }
  
  public static String generateCCAppFile(final String projectName, final String fileName, final String engine, final String additionalImports) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.emoflon.ibex.tgg.operational.strategies.cc.CC;");
    _builder.newLine();
    _builder.append(additionalImports, "");
    _builder.newLineIfNotEmpty();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append(fileName, "");
    _builder_1.append(" cc = new ");
    _builder_1.append(fileName, "");
    _builder_1.append("(\"");
    _builder_1.append(projectName, "");
    _builder_1.append("\", \"./../\", false, false);");
    _builder_1.newLineIfNotEmpty();
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Starting CC\");");
    _builder_1.newLine();
    _builder_1.append("long tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("cc.run();");
    _builder_1.newLine();
    _builder_1.append("long toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Completed CC in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("cc.saveModels();");
    _builder_1.newLine();
    _builder_1.append("cc.terminate();");
    _builder_1.newLine();
    return DefaultFilesGenerator.generateBasicStructure(_builder.toString(), fileName, 
      "CC", engine, projectName, _builder_1.toString());
  }
  
  public static CharSequence generateMetamodelRegistration() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("protected void registerUserMetamodels() throws IOException {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("//FIXME load and register source and target metamodels");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Register correspondence metamodel last");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("loadAndRegisterMetamodel(projectPath + \"/model/\" + projectPath + \".ecore\");");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
