package org.emoflon.ibex.tgg.compiler.codegen;

import java.util.Collection;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.emoflon.ibex.tgg.compiler.builder.AttrCondDefLibraryProvider;
import org.emoflon.ibex.tgg.compiler.builder.UserAttrCondHelper;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinitionLibrary;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterDefinition;
import org.moflon.core.utilities.MoflonUtil;

@SuppressWarnings("all")
public class DefaultFilesGenerator {
  public static String generateUserRuntimeAttrCondFactory(final TGGAttributeConstraintDefinitionLibrary library, final String projectName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package org.emoflon.ibex.tgg.operational.csp.constraints.custom.");
    String _lowerCase = MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase();
    _builder.append(_lowerCase);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import java.util.HashMap;");
    _builder.newLine();
    _builder.append("import java.util.HashSet;\t\t\t");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.runtime.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;");
    _builder.newLine();
    _builder.newLine();
    {
      EList<TGGAttributeConstraintDefinition> _tggAttributeConstraintDefinitions = library.getTggAttributeConstraintDefinitions();
      for(final TGGAttributeConstraintDefinition constraint : _tggAttributeConstraintDefinitions) {
        _builder.append("import ");
        _builder.append(AttrCondDefLibraryProvider.ATTR_COND_DEF_USERDEFINED_PACKAGE);
        _builder.append(".");
        String _lowerCase_1 = MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase();
        _builder.append(_lowerCase_1);
        _builder.append(".");
        String _lowerCase_2 = library.getName().toLowerCase();
        _builder.append(_lowerCase_2);
        _builder.append(".");
        String _fileName = UserAttrCondHelper.getFileName(constraint);
        _builder.append(_fileName);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("public class ");
    String _name = library.getName();
    _builder.append(_name);
    _builder.append("RuntimeTGGAttrConstraintFactory extends RuntimeTGGAttrConstraintFactory {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    String _name_1 = library.getName();
    _builder.append(_name_1, "\t");
    _builder.append("RuntimeTGGAttrConstraintFactory() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("super();");
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
    _builder.append("protected void initialize() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("creators = new HashMap<>();");
    _builder.newLine();
    {
      EList<TGGAttributeConstraintDefinition> _tggAttributeConstraintDefinitions_1 = library.getTggAttributeConstraintDefinitions();
      for(final TGGAttributeConstraintDefinition constraint_1 : _tggAttributeConstraintDefinitions_1) {
        _builder.append("\t\t");
        _builder.append("creators.put(\"");
        String _name_2 = constraint_1.getName();
        _builder.append(_name_2, "\t\t");
        _builder.append("\", () -> new ");
        String _fileName_1 = UserAttrCondHelper.getFileName(constraint_1);
        _builder.append(_fileName_1, "\t\t");
        _builder.append("());");
        _builder.newLineIfNotEmpty();
      }
    }
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
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }

  public static String generateUserAttrCondDefStub(final TGGAttributeConstraintDefinition tacd, final String projectName) {
    EObject _eContainer = tacd.eContainer();
    TGGAttributeConstraintDefinitionLibrary library = ((TGGAttributeConstraintDefinitionLibrary) _eContainer);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package org.emoflon.ibex.tgg.operational.csp.constraints.custom.");
    String _lowerCase = MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase();
    _builder.append(_lowerCase);
    _builder.append(".");
    String _lowerCase_1 = library.getName().toLowerCase();
    _builder.append(_lowerCase_1);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraint;");
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraintVariable;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    String _fileName = UserAttrCondHelper.getFileName(tacd);
    _builder.append(_fileName);
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
    String _name = tacd.getName();
    _builder.append(_name, "    ");
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
    int _size = tacd.getParameterDefinitions().size();
    _builder.append(_size, "\t\t");
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("throw new RuntimeException(\"The CSP -");
    String _upperCase = tacd.getName().toUpperCase();
    _builder.append(_upperCase, "\t\t\t");
    _builder.append("- needs exactly ");
    int _size_1 = tacd.getParameterDefinitions().size();
    _builder.append(_size_1, "\t\t\t");
    _builder.append(" variables\");");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<TGGAttributeConstraintParameterDefinition> _parameterDefinitions = tacd.getParameterDefinitions();
      for(final TGGAttributeConstraintParameterDefinition param : _parameterDefinitions) {
        _builder.append("\t\t");
        _builder.append("RuntimeTGGAttributeConstraintVariable v");
        int _indexOf = tacd.getParameterDefinitions().indexOf(param);
        _builder.append(_indexOf, "\t\t");
        _builder.append(" = variables.get(");
        int _indexOf_1 = tacd.getParameterDefinitions().indexOf(param);
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

  public static String generateBasicStructure(final String additionalImports, final String fileName, final String strategy, final String projectName, final String setUpRoutine) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package org.emoflon.ibex.tgg.run.");
    String _lowerCase = MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase();
    _builder.append(_lowerCase);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import java.io.IOException;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import org.apache.log4j.Level;");
    _builder.newLine();
    _builder.append("import org.apache.log4j.Logger;");
    _builder.newLine();
    _builder.append("import org.apache.log4j.BasicConfigurator;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.runtime.config.IRegistrationHelper;");
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.runtime.strategies.modules.TGGResourceHandler;");
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.runtime.strategies.sync.SYNC;");
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.util.ilp.ILPFactory.SupportedILPSolver;");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.run.");
    String _lowerCase_1 = MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase();
    _builder.append(_lowerCase_1);
    _builder.append(".config.*;");
    _builder.newLineIfNotEmpty();
    _builder.append(additionalImports);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public class ");
    _builder.append(fileName);
    _builder.append(" extends ");
    _builder.append(strategy);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// eMoflon supports other pattern matching engines. Replace _DefaultRegistrationHelper with one of the other registrationHelpers from the *.config-package to choose between them. Default: Democles ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static IRegistrationHelper registrationHelper = new _DefaultRegistrationHelper();");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    _builder.append(fileName, "\t");
    _builder.append("() throws IOException {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("super(registrationHelper.createIbexOptions().resourceHandler(new TGGResourceHandler() {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("public void saveModels() throws IOException {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("// Use the commented code below to implement saveModels individually.");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("// source.save(null);");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("// target.save(null);");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("// corr.save(null);");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("// protocol.save(null);");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("super.saveModels();");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("public void loadModels() throws IOException {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("// Use the commented code below to implement loadModels individually.");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("// loadResource loads from a file while createResource creates a new resource without content");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("// source = loadResource(options.project.path() + \"/instances/src.xmi\");");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("// target = createResource(options.project.path() + \"/instances/trg.xmi\");");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("// corr = createResource(options.project.path() + \"/instances/corr.xmi\");");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("// protocol = createResource(options.project.path() + \"/instances/protocol.xmi\");");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("super.loadModels();");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}));");
    _builder.newLine();
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
    _builder.append("\t\t");
    _builder.append("Logger.getRootLogger().setLevel(Level.INFO);");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append(setUpRoutine, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }

  public static String generateModelGenFile(final String projectName, final String fileName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGEN;");
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGENStopCriterion;");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("logger.info(\"Starting MODELGEN\");");
    _builder_1.newLine();
    _builder_1.append("long tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append(fileName);
    _builder_1.append(" generator = new ");
    _builder_1.append(fileName);
    _builder_1.append("();");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("long toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Completed init for MODELGEN in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("MODELGENStopCriterion stop = new MODELGENStopCriterion(generator.getTGG());");
    _builder_1.newLine();
    _builder_1.append("stop.setTimeOutInMS(1000);");
    _builder_1.newLine();
    _builder_1.append("generator.setStopCriterion(stop);");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("generator.run();");
    _builder_1.newLine();
    _builder_1.append("toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Completed MODELGEN in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("generator.saveModels();");
    _builder_1.newLine();
    _builder_1.append("generator.terminate();");
    _builder_1.newLine();
    return DefaultFilesGenerator.generateBasicStructure(_builder.toString(), fileName, 
      "MODELGEN", projectName, _builder_1.toString());
  }

  public static String generateSyncAppFile(final String projectName, final String fileName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("logger.info(\"Starting SYNC\");");
    _builder_1.newLine();
    _builder_1.append("long tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append(fileName);
    _builder_1.append(" sync = new ");
    _builder_1.append(fileName);
    _builder_1.append("();");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("long toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Completed init for SYNC in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("sync.forward();");
    _builder_1.newLine();
    _builder_1.append("toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Completed SYNC in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("sync.saveModels();");
    _builder_1.newLine();
    _builder_1.append("sync.terminate();");
    _builder_1.newLine();
    return DefaultFilesGenerator.generateBasicStructure(_builder.toString(), fileName, 
      "SYNC", projectName, _builder_1.toString());
  }

  public static String generateCCAppFile(final String projectName, final String fileName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.emoflon.ibex.tgg.operational.strategies.opt.CC;");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append(fileName);
    _builder_1.append(" cc = new ");
    _builder_1.append(fileName);
    _builder_1.append("();");
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
    _builder_1.append("logger.info(cc.generateConsistencyReport());\t\t\t");
    _builder_1.newLine();
    return DefaultFilesGenerator.generateBasicStructure(_builder.toString(), fileName, 
      "CC", projectName, _builder_1.toString());
  }

  public static String generateCOAppFile(final String projectName, final String fileName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.emoflon.ibex.tgg.operational.strategies.opt.CO;");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append(fileName);
    _builder_1.append(" co = new ");
    _builder_1.append(fileName);
    _builder_1.append("();");
    _builder_1.newLineIfNotEmpty();
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Starting CO\");");
    _builder_1.newLine();
    _builder_1.append("long tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("co.run();");
    _builder_1.newLine();
    _builder_1.append("long toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Completed CO in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("co.saveModels();");
    _builder_1.newLine();
    _builder_1.append("co.terminate();");
    _builder_1.newLine();
    _builder_1.append("logger.info(co.generateConsistencyReport());");
    _builder_1.newLine();
    return DefaultFilesGenerator.generateBasicStructure(_builder.toString(), fileName, 
      "CO", projectName, _builder_1.toString());
  }

  public static String generateFWDOptAppFile(final String projectName, final String fileName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.emoflon.ibex.tgg.operational.strategies.opt.FWD_OPT;");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append(fileName);
    _builder_1.append(" fwd_opt = new ");
    _builder_1.append(fileName);
    _builder_1.append("();");
    _builder_1.newLineIfNotEmpty();
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Starting FWD_OPT\");");
    _builder_1.newLine();
    _builder_1.append("long tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("fwd_opt.run();");
    _builder_1.newLine();
    _builder_1.append("long toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Completed FWD_OPT in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("fwd_opt.saveModels();");
    _builder_1.newLine();
    _builder_1.append("fwd_opt.terminate();");
    _builder_1.newLine();
    return DefaultFilesGenerator.generateBasicStructure(_builder.toString(), fileName, 
      "FWD_OPT", projectName, _builder_1.toString());
  }

  public static String generateBWDOptAppFile(final String projectName, final String fileName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.emoflon.ibex.tgg.operational.strategies.opt.BWD_OPT;");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append(fileName);
    _builder_1.append(" bwd_opt = new ");
    _builder_1.append(fileName);
    _builder_1.append("();");
    _builder_1.newLineIfNotEmpty();
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Starting BWD_OPT\");");
    _builder_1.newLine();
    _builder_1.append("long tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("bwd_opt.run();");
    _builder_1.newLine();
    _builder_1.append("long toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Completed BWD_OPT in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("bwd_opt.saveModels();");
    _builder_1.newLine();
    _builder_1.append("bwd_opt.terminate();");
    _builder_1.newLine();
    return DefaultFilesGenerator.generateBasicStructure(_builder.toString(), fileName, 
      "BWD_OPT", projectName, _builder_1.toString());
  }

  public static String generateInitialFwdAppFile(final String projectName, final String fileName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.emoflon.ibex.tgg.operational.strategies.sync.INITIAL_FWD;");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("logger.info(\"Starting INITIAL FWD\");");
    _builder_1.newLine();
    _builder_1.append("long tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append(fileName);
    _builder_1.append(" init_fwd = new ");
    _builder_1.append(fileName);
    _builder_1.append("();");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("long toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Completed init for FWD in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("init_fwd.forward();");
    _builder_1.newLine();
    _builder_1.append("toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Completed INITIAL_FWD in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("init_fwd.saveModels();");
    _builder_1.newLine();
    _builder_1.append("init_fwd.terminate();");
    _builder_1.newLine();
    return DefaultFilesGenerator.generateBasicStructure(_builder.toString(), fileName, 
      "INITIAL_FWD", projectName, _builder_1.toString());
  }

  public static String generateInitialBwdAppFile(final String projectName, final String fileName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.emoflon.ibex.tgg.operational.strategies.sync.INITIAL_BWD;");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("logger.info(\"Starting INITIAL BWD\");");
    _builder_1.newLine();
    _builder_1.append("long tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append(fileName);
    _builder_1.append(" init_bwd = new ");
    _builder_1.append(fileName);
    _builder_1.append("();");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("long toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Completed init for BWD in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("init_bwd.backward();");
    _builder_1.newLine();
    _builder_1.append("toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Completed INITIAL_BWD in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("init_bwd.saveModels();");
    _builder_1.newLine();
    _builder_1.append("init_bwd.terminate();");
    _builder_1.newLine();
    return DefaultFilesGenerator.generateBasicStructure(_builder.toString(), fileName, 
      "INITIAL_BWD", projectName, _builder_1.toString());
  }

  public static String generateIntegrateAppFile(final String projectName, final String fileName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("logger.info(\"Starting INTEGRATE\");");
    _builder_1.newLine();
    _builder_1.append("long tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append(fileName);
    _builder_1.append(" integrate = new ");
    _builder_1.append(fileName);
    _builder_1.append("();");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("long toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Completed init for INTEGRATE in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("integrate.integrate();");
    _builder_1.newLine();
    _builder_1.append("toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("logger.info(\"Completed INTEGRATE in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("integrate.saveModels();");
    _builder_1.newLine();
    _builder_1.append("integrate.terminate();");
    _builder_1.newLine();
    return DefaultFilesGenerator.generateBasicStructure(_builder.toString(), fileName, 
      "INTEGRATE", projectName, _builder_1.toString());
  }

  public static String generateDefaultRegHelperFile(final String projectName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package org.emoflon.ibex.tgg.run.");
    String _lowerCase = MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase();
    _builder.append(_lowerCase);
    _builder.append(".config;");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import java.io.IOException;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("import org.eclipse.emf.ecore.resource.ResourceSet;");
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.runtime.config.IRegistrationHelper;");
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;");
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.runtime.strategies.modules.IbexExecutable;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class _DefaultRegistrationHelper implements IRegistrationHelper{");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/** Load and register source and target metamodels */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void registerMetamodels(ResourceSet rs, IbexExecutable executable) throws IOException {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// Replace to register generated code or handle other URI-related requirements");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("new HiPERegistrationHelper().registerMetamodels(rs, executable);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/** Create default options **/");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public IbexOptions createIbexOptions() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return new HiPERegistrationHelper().createIbexOptions();");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
}
