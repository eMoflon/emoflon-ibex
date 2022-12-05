package org.emoflon.ibex.tgg.codegen;

import java.util.Collection;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.moflon.core.utilities.MoflonUtil;

@SuppressWarnings("all")
public class DefaultFilesGenerator {
  public static String generateUserRuntimeAttrCondFactory(final Collection<String> userDefConstraints, final String projectName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package org.emoflon.ibex.tgg.operational.csp.constraints.factories.");
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
    _builder.append("import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;\t\t\t");
    _builder.newLine();
    _builder.newLine();
    {
      for(final String constraint : userDefConstraints) {
        _builder.append("import org.emoflon.ibex.tgg.operational.csp.constraints.custom.");
        String _lowerCase_1 = MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase();
        _builder.append(_lowerCase_1);
        _builder.append(".");
        String _fileName = UserAttrCondHelper.getFileName(constraint);
        _builder.append(_fileName);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("public class UserDefinedRuntimeTGGAttrConstraintFactory extends RuntimeTGGAttrConstraintFactory {");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public UserDefinedRuntimeTGGAttrConstraintFactory() {");
    _builder.newLine();
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

  public static String generateUserAttrCondDefStub(final /* TGGAttributeConstraintDefinition */Object tacd, final String projectName) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method getParameterString(TGGAttributeConstraintDefinition) is undefined for the type Class<UserAttrCondHelper>"
      + "\nThe method getParameterString(TGGAttributeConstraintDefinition) is undefined for the type Class<UserAttrCondHelper>"
      + "\nThe method getAdorments(TGGAttributeConstraintDefinition) is undefined for the type Class<UserAttrCondHelper>"
      + "\nname cannot be resolved"
      + "\nname cannot be resolved"
      + "\nparameterDefinitions cannot be resolved"
      + "\nsize cannot be resolved"
      + "\nname cannot be resolved"
      + "\ntoUpperCase cannot be resolved"
      + "\nparameterDefinitions cannot be resolved"
      + "\nsize cannot be resolved"
      + "\nparameterDefinitions cannot be resolved"
      + "\nparameterDefinitions cannot be resolved"
      + "\nindexOf cannot be resolved"
      + "\nparameterDefinitions cannot be resolved"
      + "\nindexOf cannot be resolved");
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
    _builder.append("import org.emoflon.ibex.tgg.compiler.defaults.IRegistrationHelper;");
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.operational.strategies.modules.TGGResourceHandler;");
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

  public static String generateDebugStructure(final String additionalImports, final String fileName, final String strategy, final String projectName, final String setUpRoutine, final String body) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package org.emoflon.ibex.tgg.run.");
    String _lowerCase = MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase();
    _builder.append(_lowerCase);
    _builder.append(".debug;");
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
    _builder.append("import org.emoflon.ibex.tgg.compiler.defaults.IRegistrationHelper;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.ui.debug.adapter.TGGAdapter.IBeXOperation;");
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.ui.debug.adapter.TGGAdapter.VictoryIBeXAdapter;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.run.");
    String _lowerCase_1 = MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase();
    _builder.append(_lowerCase_1);
    _builder.append(".config.*;");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
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
    _builder.append("private static IRegistrationHelper registrationHelper = new _DefaultRegistrationHelper();");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    _builder.append(fileName, "\t");
    _builder.append("() throws IOException {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("super(registrationHelper.createIbexOptions());");
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
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(body, "\t");
    _builder.newLineIfNotEmpty();
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

  public static String generateModelGenDebugFile(final String projectName, final String fileName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGEN;");
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGENStopCriterion;");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("boolean restart = true;");
    _builder_1.newLine();
    _builder_1.append("while (restart) {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("restart = false;");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("logger.info(\"Starting MODELGEN_Debug\");");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("long tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append(fileName, "    ");
    _builder_1.append(" generator = new ");
    _builder_1.append(fileName, "    ");
    _builder_1.append("();");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("    ");
    _builder_1.append("long toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("logger.info(\"Completed init for MODELGEN_Debug in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("MODELGENStopCriterion stop = new MODELGENStopCriterion(generator.getTGG());");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("generator.setStopCriterion(stop);");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("VictoryIBeXAdapter adapter = VictoryIBeXAdapter.create(generator, IBeXOperation.MODELGEN);");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("restart = adapter.run(() -> {");
    _builder_1.newLine();
    _builder_1.append("\t       ");
    _builder_1.append("adapter.register(generator);");
    _builder_1.newLine();
    _builder_1.append("\t       ");
    _builder_1.append("try {");
    _builder_1.newLine();
    _builder_1.append("\t\t    ");
    _builder_1.append("logger.info(\"Starting MODELGEN_Debug\");");
    _builder_1.newLine();
    _builder_1.append("\t\t    ");
    _builder_1.append("long runTic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("\t\t    ");
    _builder_1.append("generator.run();");
    _builder_1.newLine();
    _builder_1.append("\t\t    ");
    _builder_1.append("long runToc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("\t\t    ");
    _builder_1.append("logger.info(\"Completed MODELGEN_Debug in: \" + (runToc - runTic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("\t\t    ");
    _builder_1.append("generator.saveModels();");
    _builder_1.newLine();
    _builder_1.append("\t\t    ");
    _builder_1.append("generator.terminate();");
    _builder_1.newLine();
    _builder_1.append("\t\t      ");
    _builder_1.append("} catch (IOException pIOE) {");
    _builder_1.newLine();
    _builder_1.append("\t\t    ");
    _builder_1.append("logger.error(\"MODELGEN_Debug threw an IOException\", pIOE);");
    _builder_1.newLine();
    _builder_1.append("\t\t      ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("});");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    return DefaultFilesGenerator.generateDebugStructure(_builder.toString(), fileName, 
      "MODELGEN", projectName, _builder_1.toString(), 
      "");
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

  public static String generateInitialFwdDebugAppFile(final String projectName, final String fileName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.emoflon.ibex.tgg.run.");
    String _lowerCase = MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase();
    _builder.append(_lowerCase);
    _builder.append(".INITIAL_FWD_App;");
    _builder.newLineIfNotEmpty();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("boolean restart = true;");
    _builder_1.newLine();
    _builder_1.append("while (restart) {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("restart = false;");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("logger.info(\"Starting INITIAL_FWD_Debug\");");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("long tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append(fileName, "    ");
    _builder_1.append(" init_fwd = new ");
    _builder_1.append(fileName, "    ");
    _builder_1.append("();");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("    ");
    _builder_1.append("long toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("logger.info(\"Completed init for INITIAL_FWD_Debug in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("VictoryIBeXAdapter adapter = VictoryIBeXAdapter.create(init_fwd, IBeXOperation.FWD);");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("restart = adapter.run(() -> {");
    _builder_1.newLine();
    _builder_1.append("        ");
    _builder_1.append("adapter.register(init_fwd);");
    _builder_1.newLine();
    _builder_1.append("        ");
    _builder_1.append("try {");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("logger.info(\"Starting INITIAL_FWD_Debug\");");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("long runTic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("init_fwd.forward();");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("long runToc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("logger.info(\"Completed INITIAL_FWD_Debug in: \" + (runToc - runTic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("\t\t    ");
    _builder_1.append("init_fwd.saveModels();");
    _builder_1.newLine();
    _builder_1.append("\t\t    ");
    _builder_1.append("init_fwd.terminate();");
    _builder_1.newLine();
    _builder_1.append("\t    ");
    _builder_1.append("} catch (IOException pIOE) {");
    _builder_1.newLine();
    _builder_1.append("\t     ");
    _builder_1.append("logger.error(\"INITIAL_FWD_Debug threw an IOException\", pIOE);");
    _builder_1.newLine();
    _builder_1.append("\t       ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("});");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    return DefaultFilesGenerator.generateDebugStructure(_builder.toString(), fileName, 
      "INITIAL_FWD_App", projectName, _builder_1.toString(), 
      "");
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

  public static String generateInitialBwdDebugAppFile(final String projectName, final String fileName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.emoflon.ibex.tgg.run.");
    String _lowerCase = MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase();
    _builder.append(_lowerCase);
    _builder.append(".INITIAL_BWD_App;");
    _builder.newLineIfNotEmpty();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("boolean restart = true;");
    _builder_1.newLine();
    _builder_1.append("while (restart) {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("restart = false;");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("logger.info(\"Starting INITIAL_BWD_Debug\");");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("long tic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append(fileName, "    ");
    _builder_1.append(" init_bwd = new ");
    _builder_1.append(fileName, "    ");
    _builder_1.append("();");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("    ");
    _builder_1.append("long toc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("logger.info(\"Completed init for INITIAL_BWD_Debug in: \" + (toc - tic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("VictoryIBeXAdapter adapter = VictoryIBeXAdapter.create(init_bwd, IBeXOperation.FWD);");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("restart = adapter.run(() -> {");
    _builder_1.newLine();
    _builder_1.append("        ");
    _builder_1.append("adapter.register(init_bwd);");
    _builder_1.newLine();
    _builder_1.append("        ");
    _builder_1.append("try {");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("logger.info(\"Starting INITIAL_BWD_Debug\");");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("long runTic = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("init_bwd.backward();");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("long runToc = System.currentTimeMillis();");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("logger.info(\"Completed INITIAL_BWD_Debug in: \" + (runToc - runTic) + \" ms\");");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("\t\t    ");
    _builder_1.append("init_bwd.saveModels();");
    _builder_1.newLine();
    _builder_1.append("\t\t    ");
    _builder_1.append("init_bwd.terminate();");
    _builder_1.newLine();
    _builder_1.append("\t    ");
    _builder_1.append("} catch (IOException pIOE) {");
    _builder_1.newLine();
    _builder_1.append("\t     ");
    _builder_1.append("logger.error(\"INITIAL_BWD_Debug threw an IOException\", pIOE);");
    _builder_1.newLine();
    _builder_1.append("\t       ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("});");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    return DefaultFilesGenerator.generateDebugStructure(_builder.toString(), fileName, 
      "INITIAL_BWD_App", projectName, _builder_1.toString(), 
      "");
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
    _builder.append("import org.emoflon.ibex.tgg.compiler.defaults.IRegistrationHelper;");
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;");
    _builder.newLine();
    _builder.append("import org.emoflon.ibex.tgg.operational.strategies.modules.IbexExecutable;");
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

  public static String generateRegHelperFile(final String projectName, final /* TripleGraphGrammarFile */Object tgg) {
    throw new Error("Unresolved compilation problems:"
      + "\nimports cannot be resolved"
      + "\nname cannot be resolved");
  }
}
