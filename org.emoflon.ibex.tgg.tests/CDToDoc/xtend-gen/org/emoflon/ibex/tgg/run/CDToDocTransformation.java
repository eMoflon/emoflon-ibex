package org.emoflon.ibex.tgg.run;

import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.viatra.query.runtime.api.IMatchProcessor;
import org.eclipse.viatra.query.runtime.api.IQuerySpecification;
import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.emf.EMFScope;
import org.eclipse.viatra.transformation.evm.api.ActivationLifeCycle;
import org.eclipse.viatra.transformation.evm.api.ExecutionSchema;
import org.eclipse.viatra.transformation.evm.specific.Lifecycles;
import org.eclipse.viatra.transformation.evm.specific.crud.CRUDActivationStateEnum;
import org.eclipse.viatra.transformation.runtime.emf.rules.EventDrivenTransformationRuleGroup;
import org.eclipse.viatra.transformation.runtime.emf.rules.eventdriven.EventDrivenTransformationRule;
import org.eclipse.viatra.transformation.runtime.emf.rules.eventdriven.EventDrivenTransformationRuleFactory;
import org.eclipse.viatra.transformation.runtime.emf.transformation.eventdriven.EventDrivenTransformation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emoflon.ibex.tgg.axiom.Axiom_BWDMatch;
import org.emoflon.ibex.tgg.axiom.Axiom_BWDMatcher;
import org.emoflon.ibex.tgg.axiom.Axiom_CCMatch;
import org.emoflon.ibex.tgg.axiom.Axiom_CCMatcher;
import org.emoflon.ibex.tgg.axiom.Axiom_FWDMatch;
import org.emoflon.ibex.tgg.axiom.Axiom_FWDMatcher;
import org.emoflon.ibex.tgg.axiom.Axiom_MODELGENMatch;
import org.emoflon.ibex.tgg.axiom.Axiom_MODELGENMatcher;
import org.emoflon.ibex.tgg.axiom.Axiom_PROTOCOLMatch;
import org.emoflon.ibex.tgg.axiom.Axiom_PROTOCOLMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirectbackwardmultirule.ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirectbackwardmultirule.ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirectbackwardmultirule.ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirectbackwardmultirule.ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirectbackwardmultirule.ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirectbackwardmultirule.ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirectbackwardmultirule.ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirectbackwardmultirule.ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirectbackwardmultirule.ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirectbackwardmultirule.ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivebackwardmultirule.ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivebackwardmultirule.ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivebackwardmultirule.ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivebackwardmultirule.ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivebackwardmultirule.ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivebackwardmultirule.ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivebackwardmultirule.ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivebackwardmultirule.ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivebackwardmultirule.ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivebackwardmultirule.ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivemultirule.ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivemultirule.ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivemultirule.ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivemultirule.ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivemultirule.ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivemultirule.ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivemultirule.ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivemultirule.ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivemultirule.ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivemultirule.ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationrule.ClazzToDocAmalgamationRule_BWDMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationrule.ClazzToDocAmalgamationRule_BWDMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationrule.ClazzToDocAmalgamationRule_CCMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationrule.ClazzToDocAmalgamationRule_CCMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationrule.ClazzToDocAmalgamationRule_FWDMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationrule.ClazzToDocAmalgamationRule_FWDMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationrule.ClazzToDocAmalgamationRule_MODELGENMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationrule.ClazzToDocAmalgamationRule_MODELGENMatcher;
import org.emoflon.ibex.tgg.clazztodocamalgamationrule.ClazzToDocAmalgamationRule_PROTOCOLMatch;
import org.emoflon.ibex.tgg.clazztodocamalgamationrule.ClazzToDocAmalgamationRule_PROTOCOLMatcher;
import org.emoflon.ibex.tgg.clazztodocfilerule.ClazzToDocFileRule_BWDMatch;
import org.emoflon.ibex.tgg.clazztodocfilerule.ClazzToDocFileRule_BWDMatcher;
import org.emoflon.ibex.tgg.clazztodocfilerule.ClazzToDocFileRule_CCMatch;
import org.emoflon.ibex.tgg.clazztodocfilerule.ClazzToDocFileRule_CCMatcher;
import org.emoflon.ibex.tgg.clazztodocfilerule.ClazzToDocFileRule_FWDMatch;
import org.emoflon.ibex.tgg.clazztodocfilerule.ClazzToDocFileRule_FWDMatcher;
import org.emoflon.ibex.tgg.clazztodocfilerule.ClazzToDocFileRule_MODELGENMatch;
import org.emoflon.ibex.tgg.clazztodocfilerule.ClazzToDocFileRule_MODELGENMatcher;
import org.emoflon.ibex.tgg.clazztodocfilerule.ClazzToDocFileRule_PROTOCOLMatch;
import org.emoflon.ibex.tgg.clazztodocfilerule.ClazzToDocFileRule_PROTOCOLMatcher;
import org.emoflon.ibex.tgg.clazztofilerule.ClazzToFileRule_BWDMatch;
import org.emoflon.ibex.tgg.clazztofilerule.ClazzToFileRule_BWDMatcher;
import org.emoflon.ibex.tgg.clazztofilerule.ClazzToFileRule_CCMatch;
import org.emoflon.ibex.tgg.clazztofilerule.ClazzToFileRule_CCMatcher;
import org.emoflon.ibex.tgg.clazztofilerule.ClazzToFileRule_FWDMatch;
import org.emoflon.ibex.tgg.clazztofilerule.ClazzToFileRule_FWDMatcher;
import org.emoflon.ibex.tgg.clazztofilerule.ClazzToFileRule_MODELGENMatch;
import org.emoflon.ibex.tgg.clazztofilerule.ClazzToFileRule_MODELGENMatcher;
import org.emoflon.ibex.tgg.clazztofilerule.ClazzToFileRule_PROTOCOLMatch;
import org.emoflon.ibex.tgg.clazztofilerule.ClazzToFileRule_PROTOCOLMatcher;
import org.emoflon.ibex.tgg.operational.OperationMode;
import org.emoflon.ibex.tgg.operational.TGGRuntimeUtil;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.CDToDocAttrCondDefLibrary;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;
import org.emoflon.ibex.tgg.packagetofolderrule_recursive.PackageToFolderRule_Recursive_BWDMatch;
import org.emoflon.ibex.tgg.packagetofolderrule_recursive.PackageToFolderRule_Recursive_BWDMatcher;
import org.emoflon.ibex.tgg.packagetofolderrule_recursive.PackageToFolderRule_Recursive_CCMatch;
import org.emoflon.ibex.tgg.packagetofolderrule_recursive.PackageToFolderRule_Recursive_CCMatcher;
import org.emoflon.ibex.tgg.packagetofolderrule_recursive.PackageToFolderRule_Recursive_FWDMatch;
import org.emoflon.ibex.tgg.packagetofolderrule_recursive.PackageToFolderRule_Recursive_FWDMatcher;
import org.emoflon.ibex.tgg.packagetofolderrule_recursive.PackageToFolderRule_Recursive_MODELGENMatch;
import org.emoflon.ibex.tgg.packagetofolderrule_recursive.PackageToFolderRule_Recursive_MODELGENMatcher;
import org.emoflon.ibex.tgg.packagetofolderrule_recursive.PackageToFolderRule_Recursive_PROTOCOLMatch;
import org.emoflon.ibex.tgg.packagetofolderrule_recursive.PackageToFolderRule_Recursive_PROTOCOLMatcher;

@SuppressWarnings("all")
public class CDToDocTransformation {
  /**
   * Transformation-related extensions
   */
  @Extension
  private EventDrivenTransformation transformation;
  
  /**
   * Transformation rule-related extensions
   */
  @Extension
  private EventDrivenTransformationRuleFactory _eventDrivenTransformationRuleFactory = new EventDrivenTransformationRuleFactory();
  
  protected ViatraQueryEngine engine;
  
  protected Resource resource;
  
  private TGGRuntimeUtil tggRuntimeUtil;
  
  public CDToDocTransformation(final ResourceSet set, final TGGRuntimeUtil tggRuntimeUtil) {
    try {
      this.resource = this.resource;
      this.tggRuntimeUtil = tggRuntimeUtil;
      RuntimeTGGAttrConstraintProvider _cSPProvider = tggRuntimeUtil.getCSPProvider();
      CDToDocAttrCondDefLibrary _cDToDocAttrCondDefLibrary = new CDToDocAttrCondDefLibrary();
      _cSPProvider.registerFactory(_cDToDocAttrCondDefLibrary);
      final EMFScope scope = new EMFScope(set);
      ViatraQueryEngine _on = ViatraQueryEngine.on(scope);
      this.engine = _on;
      Enumeration _currentLoggers = LogManager.getCurrentLoggers();
      final ArrayList<Logger> loggers = Collections.<Logger>list(_currentLoggers);
      Logger _rootLogger = LogManager.getRootLogger();
      loggers.add(_rootLogger);
      for (final Logger logger : loggers) {
        logger.setLevel(Level.OFF);
      }
      this.createTransformation();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void execute() {
    ExecutionSchema _executionSchema = this.transformation.getExecutionSchema();
    _executionSchema.startUnscheduledExecution();
  }
  
  public void dispose() {
    boolean _notEquals = (!Objects.equal(this.transformation, null));
    if (_notEquals) {
      this.transformation.dispose();
    }
    this.transformation = null;
    return;
  }
  
  private EventDrivenTransformation createTransformation() {
    try {
      EventDrivenTransformation.EventDrivenTransformationBuilder _forEngine = EventDrivenTransformation.forEngine(this.engine);
      EventDrivenTransformationRuleGroup _transformationRuleGroup = this.getTransformationRuleGroup();
      EventDrivenTransformation.EventDrivenTransformationBuilder _addRules = _forEngine.addRules(_transformationRuleGroup);
      EventDrivenTransformation _build = _addRules.build();
      return this.transformation = _build;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRuleGroup getTransformationRuleGroup() {
    if ((Objects.equal(this.tggRuntimeUtil.getMode(), OperationMode.FWD) || Objects.equal(this.tggRuntimeUtil.getMode(), OperationMode.BWD))) {
      return this.getSynch();
    } else {
      OperationMode _mode = this.tggRuntimeUtil.getMode();
      boolean _equals = Objects.equal(_mode, OperationMode.MODELGEN);
      if (_equals) {
        return this.get_MODELGEN();
      } else {
        OperationMode _mode_1 = this.tggRuntimeUtil.getMode();
        boolean _equals_1 = Objects.equal(_mode_1, OperationMode.CC);
        if (_equals_1) {
          return this.get_CC();
        }
      }
    }
    return null;
  }
  
  private EventDrivenTransformationRuleGroup getSynch() {
    EventDrivenTransformationRule<Axiom_FWDMatch, Axiom_FWDMatcher> _axiom_FWD = this.getAxiom_FWD();
    EventDrivenTransformationRule<Axiom_BWDMatch, Axiom_BWDMatcher> _axiom_BWD = this.getAxiom_BWD();
    EventDrivenTransformationRule<Axiom_PROTOCOLMatch, Axiom_PROTOCOLMatcher> _axiom_PROTOCOL = this.getAxiom_PROTOCOL();
    EventDrivenTransformationRule<ClazzToDocAmalgamationRule_FWDMatch, ClazzToDocAmalgamationRule_FWDMatcher> _clazzToDocAmalgamationRule_FWD = this.getClazzToDocAmalgamationRule_FWD();
    EventDrivenTransformationRule<ClazzToDocAmalgamationRule_BWDMatch, ClazzToDocAmalgamationRule_BWDMatcher> _clazzToDocAmalgamationRule_BWD = this.getClazzToDocAmalgamationRule_BWD();
    EventDrivenTransformationRule<ClazzToDocAmalgamationRule_PROTOCOLMatch, ClazzToDocAmalgamationRule_PROTOCOLMatcher> _clazzToDocAmalgamationRule_PROTOCOL = this.getClazzToDocAmalgamationRule_PROTOCOL();
    EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatcher> _clazzToDocAmalgamationDirectTransitiveMultiRule_FWD = this.getClazzToDocAmalgamationDirectTransitiveMultiRule_FWD();
    EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatcher> _clazzToDocAmalgamationDirectTransitiveMultiRule_BWD = this.getClazzToDocAmalgamationDirectTransitiveMultiRule_BWD();
    EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatcher> _clazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOL = this.getClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOL();
    EventDrivenTransformationRule<ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatcher> _clazzToDocAmalgamationDirectBackwardMultiRule_FWD = this.getClazzToDocAmalgamationDirectBackwardMultiRule_FWD();
    EventDrivenTransformationRule<ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatcher> _clazzToDocAmalgamationDirectBackwardMultiRule_BWD = this.getClazzToDocAmalgamationDirectBackwardMultiRule_BWD();
    EventDrivenTransformationRule<ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatcher> _clazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOL = this.getClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOL();
    EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatcher> _clazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWD = this.getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWD();
    EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatcher> _clazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWD = this.getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWD();
    EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatcher> _clazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOL = this.getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOL();
    EventDrivenTransformationRule<ClazzToDocFileRule_FWDMatch, ClazzToDocFileRule_FWDMatcher> _clazzToDocFileRule_FWD = this.getClazzToDocFileRule_FWD();
    EventDrivenTransformationRule<ClazzToDocFileRule_BWDMatch, ClazzToDocFileRule_BWDMatcher> _clazzToDocFileRule_BWD = this.getClazzToDocFileRule_BWD();
    EventDrivenTransformationRule<ClazzToDocFileRule_PROTOCOLMatch, ClazzToDocFileRule_PROTOCOLMatcher> _clazzToDocFileRule_PROTOCOL = this.getClazzToDocFileRule_PROTOCOL();
    EventDrivenTransformationRule<ClazzToFileRule_FWDMatch, ClazzToFileRule_FWDMatcher> _clazzToFileRule_FWD = this.getClazzToFileRule_FWD();
    EventDrivenTransformationRule<ClazzToFileRule_BWDMatch, ClazzToFileRule_BWDMatcher> _clazzToFileRule_BWD = this.getClazzToFileRule_BWD();
    EventDrivenTransformationRule<ClazzToFileRule_PROTOCOLMatch, ClazzToFileRule_PROTOCOLMatcher> _clazzToFileRule_PROTOCOL = this.getClazzToFileRule_PROTOCOL();
    EventDrivenTransformationRule<PackageToFolderRule_Recursive_FWDMatch, PackageToFolderRule_Recursive_FWDMatcher> _packageToFolderRule_Recursive_FWD = this.getPackageToFolderRule_Recursive_FWD();
    EventDrivenTransformationRule<PackageToFolderRule_Recursive_BWDMatch, PackageToFolderRule_Recursive_BWDMatcher> _packageToFolderRule_Recursive_BWD = this.getPackageToFolderRule_Recursive_BWD();
    EventDrivenTransformationRule<PackageToFolderRule_Recursive_PROTOCOLMatch, PackageToFolderRule_Recursive_PROTOCOLMatcher> _packageToFolderRule_Recursive_PROTOCOL = this.getPackageToFolderRule_Recursive_PROTOCOL();
    return new EventDrivenTransformationRuleGroup(_axiom_FWD, _axiom_BWD, _axiom_PROTOCOL, _clazzToDocAmalgamationRule_FWD, _clazzToDocAmalgamationRule_BWD, _clazzToDocAmalgamationRule_PROTOCOL, _clazzToDocAmalgamationDirectTransitiveMultiRule_FWD, _clazzToDocAmalgamationDirectTransitiveMultiRule_BWD, _clazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOL, _clazzToDocAmalgamationDirectBackwardMultiRule_FWD, _clazzToDocAmalgamationDirectBackwardMultiRule_BWD, _clazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOL, _clazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWD, _clazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWD, _clazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOL, _clazzToDocFileRule_FWD, _clazzToDocFileRule_BWD, _clazzToDocFileRule_PROTOCOL, _clazzToFileRule_FWD, _clazzToFileRule_BWD, _clazzToFileRule_PROTOCOL, _packageToFolderRule_Recursive_FWD, _packageToFolderRule_Recursive_BWD, _packageToFolderRule_Recursive_PROTOCOL);
  }
  
  private EventDrivenTransformationRuleGroup get_MODELGEN() {
    EventDrivenTransformationRule<Axiom_MODELGENMatch, Axiom_MODELGENMatcher> _axiom_MODELGEN = this.getAxiom_MODELGEN();
    EventDrivenTransformationRule<ClazzToDocAmalgamationRule_MODELGENMatch, ClazzToDocAmalgamationRule_MODELGENMatcher> _clazzToDocAmalgamationRule_MODELGEN = this.getClazzToDocAmalgamationRule_MODELGEN();
    EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatcher> _clazzToDocAmalgamationDirectTransitiveMultiRule_MODELGEN = this.getClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGEN();
    EventDrivenTransformationRule<ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatcher> _clazzToDocAmalgamationDirectBackwardMultiRule_MODELGEN = this.getClazzToDocAmalgamationDirectBackwardMultiRule_MODELGEN();
    EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatcher> _clazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGEN = this.getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGEN();
    EventDrivenTransformationRule<ClazzToDocFileRule_MODELGENMatch, ClazzToDocFileRule_MODELGENMatcher> _clazzToDocFileRule_MODELGEN = this.getClazzToDocFileRule_MODELGEN();
    EventDrivenTransformationRule<ClazzToFileRule_MODELGENMatch, ClazzToFileRule_MODELGENMatcher> _clazzToFileRule_MODELGEN = this.getClazzToFileRule_MODELGEN();
    EventDrivenTransformationRule<PackageToFolderRule_Recursive_MODELGENMatch, PackageToFolderRule_Recursive_MODELGENMatcher> _packageToFolderRule_Recursive_MODELGEN = this.getPackageToFolderRule_Recursive_MODELGEN();
    return new EventDrivenTransformationRuleGroup(_axiom_MODELGEN, _clazzToDocAmalgamationRule_MODELGEN, _clazzToDocAmalgamationDirectTransitiveMultiRule_MODELGEN, _clazzToDocAmalgamationDirectBackwardMultiRule_MODELGEN, _clazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGEN, _clazzToDocFileRule_MODELGEN, _clazzToFileRule_MODELGEN, _packageToFolderRule_Recursive_MODELGEN);
  }
  
  private EventDrivenTransformationRuleGroup get_CC() {
    EventDrivenTransformationRule<Axiom_CCMatch, Axiom_CCMatcher> _axiom_CC = this.getAxiom_CC();
    EventDrivenTransformationRule<ClazzToDocAmalgamationRule_CCMatch, ClazzToDocAmalgamationRule_CCMatcher> _clazzToDocAmalgamationRule_CC = this.getClazzToDocAmalgamationRule_CC();
    EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatcher> _clazzToDocAmalgamationDirectTransitiveMultiRule_CC = this.getClazzToDocAmalgamationDirectTransitiveMultiRule_CC();
    EventDrivenTransformationRule<ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatcher> _clazzToDocAmalgamationDirectBackwardMultiRule_CC = this.getClazzToDocAmalgamationDirectBackwardMultiRule_CC();
    EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatcher> _clazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CC = this.getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CC();
    EventDrivenTransformationRule<ClazzToDocFileRule_CCMatch, ClazzToDocFileRule_CCMatcher> _clazzToDocFileRule_CC = this.getClazzToDocFileRule_CC();
    EventDrivenTransformationRule<ClazzToFileRule_CCMatch, ClazzToFileRule_CCMatcher> _clazzToFileRule_CC = this.getClazzToFileRule_CC();
    EventDrivenTransformationRule<PackageToFolderRule_Recursive_CCMatch, PackageToFolderRule_Recursive_CCMatcher> _packageToFolderRule_Recursive_CC = this.getPackageToFolderRule_Recursive_CC();
    return new EventDrivenTransformationRuleGroup(_axiom_CC, _clazzToDocAmalgamationRule_CC, _clazzToDocAmalgamationDirectTransitiveMultiRule_CC, _clazzToDocAmalgamationDirectBackwardMultiRule_CC, _clazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CC, _clazzToDocFileRule_CC, _clazzToFileRule_CC, _packageToFolderRule_Recursive_CC);
  }
  
  private EventDrivenTransformationRule<Axiom_FWDMatch, Axiom_FWDMatcher> getAxiom_FWD() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_FWDMatch, Axiom_FWDMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<Axiom_FWDMatch, Axiom_FWDMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_FWDMatch, Axiom_FWDMatcher> _name = _createRule.name("Axiom_FWD");
      IQuerySpecification<Axiom_FWDMatcher> _querySpecification = Axiom_FWDMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_FWDMatch, Axiom_FWDMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<Axiom_FWDMatch> _function = (Axiom_FWDMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("Axiom", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_FWDMatch, Axiom_FWDMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<Axiom_FWDMatch> _function_1 = (Axiom_FWDMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_FWDMatch, Axiom_FWDMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_FWDMatch, Axiom_FWDMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationRule_FWDMatch, ClazzToDocAmalgamationRule_FWDMatcher> getClazzToDocAmalgamationRule_FWD() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_FWDMatch, ClazzToDocAmalgamationRule_FWDMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationRule_FWDMatch, ClazzToDocAmalgamationRule_FWDMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_FWDMatch, ClazzToDocAmalgamationRule_FWDMatcher> _name = _createRule.name("ClazzToDocAmalgamationRule_FWD");
      IQuerySpecification<ClazzToDocAmalgamationRule_FWDMatcher> _querySpecification = ClazzToDocAmalgamationRule_FWDMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_FWDMatch, ClazzToDocAmalgamationRule_FWDMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationRule_FWDMatch> _function = (ClazzToDocAmalgamationRule_FWDMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_FWDMatch, ClazzToDocAmalgamationRule_FWDMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationRule_FWDMatch> _function_1 = (ClazzToDocAmalgamationRule_FWDMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_FWDMatch, ClazzToDocAmalgamationRule_FWDMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_FWDMatch, ClazzToDocAmalgamationRule_FWDMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatcher> getClazzToDocAmalgamationDirectTransitiveMultiRule_FWD() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatcher> _name = _createRule.name("ClazzToDocAmalgamationDirectTransitiveMultiRule_FWD");
      IQuerySpecification<ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatcher> _querySpecification = ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatch> _function = (ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectTransitiveMultiRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatch> _function_1 = (ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatcher> getClazzToDocAmalgamationDirectBackwardMultiRule_FWD() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatcher> _name = _createRule.name("ClazzToDocAmalgamationDirectBackwardMultiRule_FWD");
      IQuerySpecification<ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatcher> _querySpecification = ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatch> _function = (ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectBackwardMultiRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatch> _function_1 = (ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatcher> getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWD() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatcher> _name = _createRule.name("ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWD");
      IQuerySpecification<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatcher> _querySpecification = ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatch> _function = (ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatch> _function_1 = (ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocFileRule_FWDMatch, ClazzToDocFileRule_FWDMatcher> getClazzToDocFileRule_FWD() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_FWDMatch, ClazzToDocFileRule_FWDMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocFileRule_FWDMatch, ClazzToDocFileRule_FWDMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_FWDMatch, ClazzToDocFileRule_FWDMatcher> _name = _createRule.name("ClazzToDocFileRule_FWD");
      IQuerySpecification<ClazzToDocFileRule_FWDMatcher> _querySpecification = ClazzToDocFileRule_FWDMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_FWDMatch, ClazzToDocFileRule_FWDMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocFileRule_FWDMatch> _function = (ClazzToDocFileRule_FWDMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocFileRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_FWDMatch, ClazzToDocFileRule_FWDMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocFileRule_FWDMatch> _function_1 = (ClazzToDocFileRule_FWDMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_FWDMatch, ClazzToDocFileRule_FWDMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_FWDMatch, ClazzToDocFileRule_FWDMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToFileRule_FWDMatch, ClazzToFileRule_FWDMatcher> getClazzToFileRule_FWD() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_FWDMatch, ClazzToFileRule_FWDMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToFileRule_FWDMatch, ClazzToFileRule_FWDMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_FWDMatch, ClazzToFileRule_FWDMatcher> _name = _createRule.name("ClazzToFileRule_FWD");
      IQuerySpecification<ClazzToFileRule_FWDMatcher> _querySpecification = ClazzToFileRule_FWDMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_FWDMatch, ClazzToFileRule_FWDMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToFileRule_FWDMatch> _function = (ClazzToFileRule_FWDMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToFileRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_FWDMatch, ClazzToFileRule_FWDMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToFileRule_FWDMatch> _function_1 = (ClazzToFileRule_FWDMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_FWDMatch, ClazzToFileRule_FWDMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_FWDMatch, ClazzToFileRule_FWDMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<PackageToFolderRule_Recursive_FWDMatch, PackageToFolderRule_Recursive_FWDMatcher> getPackageToFolderRule_Recursive_FWD() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_FWDMatch, PackageToFolderRule_Recursive_FWDMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<PackageToFolderRule_Recursive_FWDMatch, PackageToFolderRule_Recursive_FWDMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_FWDMatch, PackageToFolderRule_Recursive_FWDMatcher> _name = _createRule.name("PackageToFolderRule_Recursive_FWD");
      IQuerySpecification<PackageToFolderRule_Recursive_FWDMatcher> _querySpecification = PackageToFolderRule_Recursive_FWDMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_FWDMatch, PackageToFolderRule_Recursive_FWDMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<PackageToFolderRule_Recursive_FWDMatch> _function = (PackageToFolderRule_Recursive_FWDMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("PackageToFolderRule_Recursive", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_FWDMatch, PackageToFolderRule_Recursive_FWDMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<PackageToFolderRule_Recursive_FWDMatch> _function_1 = (PackageToFolderRule_Recursive_FWDMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_FWDMatch, PackageToFolderRule_Recursive_FWDMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_FWDMatch, PackageToFolderRule_Recursive_FWDMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<Axiom_PROTOCOLMatch, Axiom_PROTOCOLMatcher> getAxiom_PROTOCOL() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_PROTOCOLMatch, Axiom_PROTOCOLMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<Axiom_PROTOCOLMatch, Axiom_PROTOCOLMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_PROTOCOLMatch, Axiom_PROTOCOLMatcher> _name = _createRule.name("Axiom_PROTOCOL");
      IQuerySpecification<Axiom_PROTOCOLMatcher> _querySpecification = Axiom_PROTOCOLMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_PROTOCOLMatch, Axiom_PROTOCOLMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<Axiom_PROTOCOLMatch> _function = (Axiom_PROTOCOLMatch it) -> {
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_PROTOCOLMatch, Axiom_PROTOCOLMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<Axiom_PROTOCOLMatch> _function_1 = (Axiom_PROTOCOLMatch it) -> {
        this.tggRuntimeUtil.addBrokenMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_PROTOCOLMatch, Axiom_PROTOCOLMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_PROTOCOLMatch, Axiom_PROTOCOLMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationRule_PROTOCOLMatch, ClazzToDocAmalgamationRule_PROTOCOLMatcher> getClazzToDocAmalgamationRule_PROTOCOL() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_PROTOCOLMatch, ClazzToDocAmalgamationRule_PROTOCOLMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationRule_PROTOCOLMatch, ClazzToDocAmalgamationRule_PROTOCOLMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_PROTOCOLMatch, ClazzToDocAmalgamationRule_PROTOCOLMatcher> _name = _createRule.name("ClazzToDocAmalgamationRule_PROTOCOL");
      IQuerySpecification<ClazzToDocAmalgamationRule_PROTOCOLMatcher> _querySpecification = ClazzToDocAmalgamationRule_PROTOCOLMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_PROTOCOLMatch, ClazzToDocAmalgamationRule_PROTOCOLMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationRule_PROTOCOLMatch> _function = (ClazzToDocAmalgamationRule_PROTOCOLMatch it) -> {
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_PROTOCOLMatch, ClazzToDocAmalgamationRule_PROTOCOLMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationRule_PROTOCOLMatch> _function_1 = (ClazzToDocAmalgamationRule_PROTOCOLMatch it) -> {
        this.tggRuntimeUtil.addBrokenMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_PROTOCOLMatch, ClazzToDocAmalgamationRule_PROTOCOLMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_PROTOCOLMatch, ClazzToDocAmalgamationRule_PROTOCOLMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatcher> getClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOL() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatcher> _name = _createRule.name("ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOL");
      IQuerySpecification<ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatcher> _querySpecification = ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatch> _function = (ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatch it) -> {
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatch> _function_1 = (ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatch it) -> {
        this.tggRuntimeUtil.addBrokenMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatcher> getClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOL() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatcher> _name = _createRule.name("ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOL");
      IQuerySpecification<ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatcher> _querySpecification = ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatch> _function = (ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatch it) -> {
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatch> _function_1 = (ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatch it) -> {
        this.tggRuntimeUtil.addBrokenMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatcher> getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOL() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatcher> _name = _createRule.name("ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOL");
      IQuerySpecification<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatcher> _querySpecification = ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatch> _function = (ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatch it) -> {
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatch> _function_1 = (ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatch it) -> {
        this.tggRuntimeUtil.addBrokenMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocFileRule_PROTOCOLMatch, ClazzToDocFileRule_PROTOCOLMatcher> getClazzToDocFileRule_PROTOCOL() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_PROTOCOLMatch, ClazzToDocFileRule_PROTOCOLMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocFileRule_PROTOCOLMatch, ClazzToDocFileRule_PROTOCOLMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_PROTOCOLMatch, ClazzToDocFileRule_PROTOCOLMatcher> _name = _createRule.name("ClazzToDocFileRule_PROTOCOL");
      IQuerySpecification<ClazzToDocFileRule_PROTOCOLMatcher> _querySpecification = ClazzToDocFileRule_PROTOCOLMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_PROTOCOLMatch, ClazzToDocFileRule_PROTOCOLMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocFileRule_PROTOCOLMatch> _function = (ClazzToDocFileRule_PROTOCOLMatch it) -> {
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_PROTOCOLMatch, ClazzToDocFileRule_PROTOCOLMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocFileRule_PROTOCOLMatch> _function_1 = (ClazzToDocFileRule_PROTOCOLMatch it) -> {
        this.tggRuntimeUtil.addBrokenMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_PROTOCOLMatch, ClazzToDocFileRule_PROTOCOLMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_PROTOCOLMatch, ClazzToDocFileRule_PROTOCOLMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToFileRule_PROTOCOLMatch, ClazzToFileRule_PROTOCOLMatcher> getClazzToFileRule_PROTOCOL() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_PROTOCOLMatch, ClazzToFileRule_PROTOCOLMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToFileRule_PROTOCOLMatch, ClazzToFileRule_PROTOCOLMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_PROTOCOLMatch, ClazzToFileRule_PROTOCOLMatcher> _name = _createRule.name("ClazzToFileRule_PROTOCOL");
      IQuerySpecification<ClazzToFileRule_PROTOCOLMatcher> _querySpecification = ClazzToFileRule_PROTOCOLMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_PROTOCOLMatch, ClazzToFileRule_PROTOCOLMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToFileRule_PROTOCOLMatch> _function = (ClazzToFileRule_PROTOCOLMatch it) -> {
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_PROTOCOLMatch, ClazzToFileRule_PROTOCOLMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToFileRule_PROTOCOLMatch> _function_1 = (ClazzToFileRule_PROTOCOLMatch it) -> {
        this.tggRuntimeUtil.addBrokenMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_PROTOCOLMatch, ClazzToFileRule_PROTOCOLMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_PROTOCOLMatch, ClazzToFileRule_PROTOCOLMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<PackageToFolderRule_Recursive_PROTOCOLMatch, PackageToFolderRule_Recursive_PROTOCOLMatcher> getPackageToFolderRule_Recursive_PROTOCOL() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_PROTOCOLMatch, PackageToFolderRule_Recursive_PROTOCOLMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<PackageToFolderRule_Recursive_PROTOCOLMatch, PackageToFolderRule_Recursive_PROTOCOLMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_PROTOCOLMatch, PackageToFolderRule_Recursive_PROTOCOLMatcher> _name = _createRule.name("PackageToFolderRule_Recursive_PROTOCOL");
      IQuerySpecification<PackageToFolderRule_Recursive_PROTOCOLMatcher> _querySpecification = PackageToFolderRule_Recursive_PROTOCOLMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_PROTOCOLMatch, PackageToFolderRule_Recursive_PROTOCOLMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<PackageToFolderRule_Recursive_PROTOCOLMatch> _function = (PackageToFolderRule_Recursive_PROTOCOLMatch it) -> {
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_PROTOCOLMatch, PackageToFolderRule_Recursive_PROTOCOLMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<PackageToFolderRule_Recursive_PROTOCOLMatch> _function_1 = (PackageToFolderRule_Recursive_PROTOCOLMatch it) -> {
        this.tggRuntimeUtil.addBrokenMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_PROTOCOLMatch, PackageToFolderRule_Recursive_PROTOCOLMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_PROTOCOLMatch, PackageToFolderRule_Recursive_PROTOCOLMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<Axiom_MODELGENMatch, Axiom_MODELGENMatcher> getAxiom_MODELGEN() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_MODELGENMatch, Axiom_MODELGENMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<Axiom_MODELGENMatch, Axiom_MODELGENMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_MODELGENMatch, Axiom_MODELGENMatcher> _name = _createRule.name("Axiom_MODELGEN");
      IQuerySpecification<Axiom_MODELGENMatcher> _querySpecification = Axiom_MODELGENMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_MODELGENMatch, Axiom_MODELGENMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<Axiom_MODELGENMatch> _function = (Axiom_MODELGENMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("Axiom", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_MODELGENMatch, Axiom_MODELGENMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<Axiom_MODELGENMatch> _function_1 = (Axiom_MODELGENMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_MODELGENMatch, Axiom_MODELGENMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_MODELGENMatch, Axiom_MODELGENMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationRule_MODELGENMatch, ClazzToDocAmalgamationRule_MODELGENMatcher> getClazzToDocAmalgamationRule_MODELGEN() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_MODELGENMatch, ClazzToDocAmalgamationRule_MODELGENMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationRule_MODELGENMatch, ClazzToDocAmalgamationRule_MODELGENMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_MODELGENMatch, ClazzToDocAmalgamationRule_MODELGENMatcher> _name = _createRule.name("ClazzToDocAmalgamationRule_MODELGEN");
      IQuerySpecification<ClazzToDocAmalgamationRule_MODELGENMatcher> _querySpecification = ClazzToDocAmalgamationRule_MODELGENMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_MODELGENMatch, ClazzToDocAmalgamationRule_MODELGENMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationRule_MODELGENMatch> _function = (ClazzToDocAmalgamationRule_MODELGENMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_MODELGENMatch, ClazzToDocAmalgamationRule_MODELGENMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationRule_MODELGENMatch> _function_1 = (ClazzToDocAmalgamationRule_MODELGENMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_MODELGENMatch, ClazzToDocAmalgamationRule_MODELGENMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_MODELGENMatch, ClazzToDocAmalgamationRule_MODELGENMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatcher> getClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGEN() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatcher> _name = _createRule.name("ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGEN");
      IQuerySpecification<ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatcher> _querySpecification = ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatch> _function = (ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectTransitiveMultiRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatch> _function_1 = (ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatcher> getClazzToDocAmalgamationDirectBackwardMultiRule_MODELGEN() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatcher> _name = _createRule.name("ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGEN");
      IQuerySpecification<ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatcher> _querySpecification = ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatch> _function = (ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectBackwardMultiRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatch> _function_1 = (ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatcher> getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGEN() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatcher> _name = _createRule.name("ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGEN");
      IQuerySpecification<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatcher> _querySpecification = ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatch> _function = (ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatch> _function_1 = (ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocFileRule_MODELGENMatch, ClazzToDocFileRule_MODELGENMatcher> getClazzToDocFileRule_MODELGEN() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_MODELGENMatch, ClazzToDocFileRule_MODELGENMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocFileRule_MODELGENMatch, ClazzToDocFileRule_MODELGENMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_MODELGENMatch, ClazzToDocFileRule_MODELGENMatcher> _name = _createRule.name("ClazzToDocFileRule_MODELGEN");
      IQuerySpecification<ClazzToDocFileRule_MODELGENMatcher> _querySpecification = ClazzToDocFileRule_MODELGENMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_MODELGENMatch, ClazzToDocFileRule_MODELGENMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocFileRule_MODELGENMatch> _function = (ClazzToDocFileRule_MODELGENMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocFileRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_MODELGENMatch, ClazzToDocFileRule_MODELGENMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocFileRule_MODELGENMatch> _function_1 = (ClazzToDocFileRule_MODELGENMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_MODELGENMatch, ClazzToDocFileRule_MODELGENMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_MODELGENMatch, ClazzToDocFileRule_MODELGENMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToFileRule_MODELGENMatch, ClazzToFileRule_MODELGENMatcher> getClazzToFileRule_MODELGEN() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_MODELGENMatch, ClazzToFileRule_MODELGENMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToFileRule_MODELGENMatch, ClazzToFileRule_MODELGENMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_MODELGENMatch, ClazzToFileRule_MODELGENMatcher> _name = _createRule.name("ClazzToFileRule_MODELGEN");
      IQuerySpecification<ClazzToFileRule_MODELGENMatcher> _querySpecification = ClazzToFileRule_MODELGENMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_MODELGENMatch, ClazzToFileRule_MODELGENMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToFileRule_MODELGENMatch> _function = (ClazzToFileRule_MODELGENMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToFileRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_MODELGENMatch, ClazzToFileRule_MODELGENMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToFileRule_MODELGENMatch> _function_1 = (ClazzToFileRule_MODELGENMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_MODELGENMatch, ClazzToFileRule_MODELGENMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_MODELGENMatch, ClazzToFileRule_MODELGENMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<PackageToFolderRule_Recursive_MODELGENMatch, PackageToFolderRule_Recursive_MODELGENMatcher> getPackageToFolderRule_Recursive_MODELGEN() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_MODELGENMatch, PackageToFolderRule_Recursive_MODELGENMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<PackageToFolderRule_Recursive_MODELGENMatch, PackageToFolderRule_Recursive_MODELGENMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_MODELGENMatch, PackageToFolderRule_Recursive_MODELGENMatcher> _name = _createRule.name("PackageToFolderRule_Recursive_MODELGEN");
      IQuerySpecification<PackageToFolderRule_Recursive_MODELGENMatcher> _querySpecification = PackageToFolderRule_Recursive_MODELGENMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_MODELGENMatch, PackageToFolderRule_Recursive_MODELGENMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<PackageToFolderRule_Recursive_MODELGENMatch> _function = (PackageToFolderRule_Recursive_MODELGENMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("PackageToFolderRule_Recursive", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_MODELGENMatch, PackageToFolderRule_Recursive_MODELGENMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<PackageToFolderRule_Recursive_MODELGENMatch> _function_1 = (PackageToFolderRule_Recursive_MODELGENMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_MODELGENMatch, PackageToFolderRule_Recursive_MODELGENMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_MODELGENMatch, PackageToFolderRule_Recursive_MODELGENMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<Axiom_BWDMatch, Axiom_BWDMatcher> getAxiom_BWD() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_BWDMatch, Axiom_BWDMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<Axiom_BWDMatch, Axiom_BWDMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_BWDMatch, Axiom_BWDMatcher> _name = _createRule.name("Axiom_BWD");
      IQuerySpecification<Axiom_BWDMatcher> _querySpecification = Axiom_BWDMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_BWDMatch, Axiom_BWDMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<Axiom_BWDMatch> _function = (Axiom_BWDMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("Axiom", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_BWDMatch, Axiom_BWDMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<Axiom_BWDMatch> _function_1 = (Axiom_BWDMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_BWDMatch, Axiom_BWDMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_BWDMatch, Axiom_BWDMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationRule_BWDMatch, ClazzToDocAmalgamationRule_BWDMatcher> getClazzToDocAmalgamationRule_BWD() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_BWDMatch, ClazzToDocAmalgamationRule_BWDMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationRule_BWDMatch, ClazzToDocAmalgamationRule_BWDMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_BWDMatch, ClazzToDocAmalgamationRule_BWDMatcher> _name = _createRule.name("ClazzToDocAmalgamationRule_BWD");
      IQuerySpecification<ClazzToDocAmalgamationRule_BWDMatcher> _querySpecification = ClazzToDocAmalgamationRule_BWDMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_BWDMatch, ClazzToDocAmalgamationRule_BWDMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationRule_BWDMatch> _function = (ClazzToDocAmalgamationRule_BWDMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_BWDMatch, ClazzToDocAmalgamationRule_BWDMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationRule_BWDMatch> _function_1 = (ClazzToDocAmalgamationRule_BWDMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_BWDMatch, ClazzToDocAmalgamationRule_BWDMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_BWDMatch, ClazzToDocAmalgamationRule_BWDMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatcher> getClazzToDocAmalgamationDirectTransitiveMultiRule_BWD() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatcher> _name = _createRule.name("ClazzToDocAmalgamationDirectTransitiveMultiRule_BWD");
      IQuerySpecification<ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatcher> _querySpecification = ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatch> _function = (ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectTransitiveMultiRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatch> _function_1 = (ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatcher> getClazzToDocAmalgamationDirectBackwardMultiRule_BWD() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatcher> _name = _createRule.name("ClazzToDocAmalgamationDirectBackwardMultiRule_BWD");
      IQuerySpecification<ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatcher> _querySpecification = ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatch> _function = (ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectBackwardMultiRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatch> _function_1 = (ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatcher> getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWD() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatcher> _name = _createRule.name("ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWD");
      IQuerySpecification<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatcher> _querySpecification = ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatch> _function = (ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatch> _function_1 = (ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocFileRule_BWDMatch, ClazzToDocFileRule_BWDMatcher> getClazzToDocFileRule_BWD() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_BWDMatch, ClazzToDocFileRule_BWDMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocFileRule_BWDMatch, ClazzToDocFileRule_BWDMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_BWDMatch, ClazzToDocFileRule_BWDMatcher> _name = _createRule.name("ClazzToDocFileRule_BWD");
      IQuerySpecification<ClazzToDocFileRule_BWDMatcher> _querySpecification = ClazzToDocFileRule_BWDMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_BWDMatch, ClazzToDocFileRule_BWDMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocFileRule_BWDMatch> _function = (ClazzToDocFileRule_BWDMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocFileRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_BWDMatch, ClazzToDocFileRule_BWDMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocFileRule_BWDMatch> _function_1 = (ClazzToDocFileRule_BWDMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_BWDMatch, ClazzToDocFileRule_BWDMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_BWDMatch, ClazzToDocFileRule_BWDMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToFileRule_BWDMatch, ClazzToFileRule_BWDMatcher> getClazzToFileRule_BWD() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_BWDMatch, ClazzToFileRule_BWDMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToFileRule_BWDMatch, ClazzToFileRule_BWDMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_BWDMatch, ClazzToFileRule_BWDMatcher> _name = _createRule.name("ClazzToFileRule_BWD");
      IQuerySpecification<ClazzToFileRule_BWDMatcher> _querySpecification = ClazzToFileRule_BWDMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_BWDMatch, ClazzToFileRule_BWDMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToFileRule_BWDMatch> _function = (ClazzToFileRule_BWDMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToFileRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_BWDMatch, ClazzToFileRule_BWDMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToFileRule_BWDMatch> _function_1 = (ClazzToFileRule_BWDMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_BWDMatch, ClazzToFileRule_BWDMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_BWDMatch, ClazzToFileRule_BWDMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<PackageToFolderRule_Recursive_BWDMatch, PackageToFolderRule_Recursive_BWDMatcher> getPackageToFolderRule_Recursive_BWD() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_BWDMatch, PackageToFolderRule_Recursive_BWDMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<PackageToFolderRule_Recursive_BWDMatch, PackageToFolderRule_Recursive_BWDMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_BWDMatch, PackageToFolderRule_Recursive_BWDMatcher> _name = _createRule.name("PackageToFolderRule_Recursive_BWD");
      IQuerySpecification<PackageToFolderRule_Recursive_BWDMatcher> _querySpecification = PackageToFolderRule_Recursive_BWDMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_BWDMatch, PackageToFolderRule_Recursive_BWDMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<PackageToFolderRule_Recursive_BWDMatch> _function = (PackageToFolderRule_Recursive_BWDMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("PackageToFolderRule_Recursive", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_BWDMatch, PackageToFolderRule_Recursive_BWDMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<PackageToFolderRule_Recursive_BWDMatch> _function_1 = (PackageToFolderRule_Recursive_BWDMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_BWDMatch, PackageToFolderRule_Recursive_BWDMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_BWDMatch, PackageToFolderRule_Recursive_BWDMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<Axiom_CCMatch, Axiom_CCMatcher> getAxiom_CC() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_CCMatch, Axiom_CCMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<Axiom_CCMatch, Axiom_CCMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_CCMatch, Axiom_CCMatcher> _name = _createRule.name("Axiom_CC");
      IQuerySpecification<Axiom_CCMatcher> _querySpecification = Axiom_CCMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_CCMatch, Axiom_CCMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<Axiom_CCMatch> _function = (Axiom_CCMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("Axiom", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_CCMatch, Axiom_CCMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<Axiom_CCMatch> _function_1 = (Axiom_CCMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_CCMatch, Axiom_CCMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<Axiom_CCMatch, Axiom_CCMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationRule_CCMatch, ClazzToDocAmalgamationRule_CCMatcher> getClazzToDocAmalgamationRule_CC() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_CCMatch, ClazzToDocAmalgamationRule_CCMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationRule_CCMatch, ClazzToDocAmalgamationRule_CCMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_CCMatch, ClazzToDocAmalgamationRule_CCMatcher> _name = _createRule.name("ClazzToDocAmalgamationRule_CC");
      IQuerySpecification<ClazzToDocAmalgamationRule_CCMatcher> _querySpecification = ClazzToDocAmalgamationRule_CCMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_CCMatch, ClazzToDocAmalgamationRule_CCMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationRule_CCMatch> _function = (ClazzToDocAmalgamationRule_CCMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_CCMatch, ClazzToDocAmalgamationRule_CCMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationRule_CCMatch> _function_1 = (ClazzToDocAmalgamationRule_CCMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_CCMatch, ClazzToDocAmalgamationRule_CCMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationRule_CCMatch, ClazzToDocAmalgamationRule_CCMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatcher> getClazzToDocAmalgamationDirectTransitiveMultiRule_CC() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatcher> _name = _createRule.name("ClazzToDocAmalgamationDirectTransitiveMultiRule_CC");
      IQuerySpecification<ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatcher> _querySpecification = ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatch> _function = (ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectTransitiveMultiRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatch> _function_1 = (ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatch, ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatcher> getClazzToDocAmalgamationDirectBackwardMultiRule_CC() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatcher> _name = _createRule.name("ClazzToDocAmalgamationDirectBackwardMultiRule_CC");
      IQuerySpecification<ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatcher> _querySpecification = ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatch> _function = (ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectBackwardMultiRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatch> _function_1 = (ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatch, ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatcher> getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CC() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatcher> _name = _createRule.name("ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CC");
      IQuerySpecification<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatcher> _querySpecification = ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatch> _function = (ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatch> _function_1 = (ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatch, ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToDocFileRule_CCMatch, ClazzToDocFileRule_CCMatcher> getClazzToDocFileRule_CC() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_CCMatch, ClazzToDocFileRule_CCMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToDocFileRule_CCMatch, ClazzToDocFileRule_CCMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_CCMatch, ClazzToDocFileRule_CCMatcher> _name = _createRule.name("ClazzToDocFileRule_CC");
      IQuerySpecification<ClazzToDocFileRule_CCMatcher> _querySpecification = ClazzToDocFileRule_CCMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_CCMatch, ClazzToDocFileRule_CCMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToDocFileRule_CCMatch> _function = (ClazzToDocFileRule_CCMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocFileRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_CCMatch, ClazzToDocFileRule_CCMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToDocFileRule_CCMatch> _function_1 = (ClazzToDocFileRule_CCMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_CCMatch, ClazzToDocFileRule_CCMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToDocFileRule_CCMatch, ClazzToDocFileRule_CCMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<ClazzToFileRule_CCMatch, ClazzToFileRule_CCMatcher> getClazzToFileRule_CC() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_CCMatch, ClazzToFileRule_CCMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<ClazzToFileRule_CCMatch, ClazzToFileRule_CCMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_CCMatch, ClazzToFileRule_CCMatcher> _name = _createRule.name("ClazzToFileRule_CC");
      IQuerySpecification<ClazzToFileRule_CCMatcher> _querySpecification = ClazzToFileRule_CCMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_CCMatch, ClazzToFileRule_CCMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<ClazzToFileRule_CCMatch> _function = (ClazzToFileRule_CCMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("ClazzToFileRule", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_CCMatch, ClazzToFileRule_CCMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<ClazzToFileRule_CCMatch> _function_1 = (ClazzToFileRule_CCMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_CCMatch, ClazzToFileRule_CCMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<ClazzToFileRule_CCMatch, ClazzToFileRule_CCMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private EventDrivenTransformationRule<PackageToFolderRule_Recursive_CCMatch, PackageToFolderRule_Recursive_CCMatcher> getPackageToFolderRule_Recursive_CC() {
    try {
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_CCMatch, PackageToFolderRule_Recursive_CCMatcher> _createRule = this._eventDrivenTransformationRuleFactory.<PackageToFolderRule_Recursive_CCMatch, PackageToFolderRule_Recursive_CCMatcher>createRule();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_CCMatch, PackageToFolderRule_Recursive_CCMatcher> _name = _createRule.name("PackageToFolderRule_Recursive_CC");
      IQuerySpecification<PackageToFolderRule_Recursive_CCMatcher> _querySpecification = PackageToFolderRule_Recursive_CCMatcher.querySpecification();
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_CCMatch, PackageToFolderRule_Recursive_CCMatcher> _precondition = _name.precondition(_querySpecification);
      final IMatchProcessor<PackageToFolderRule_Recursive_CCMatch> _function = (PackageToFolderRule_Recursive_CCMatch it) -> {
        this.tggRuntimeUtil.addOperationalRuleMatch("PackageToFolderRule_Recursive", it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_CCMatch, PackageToFolderRule_Recursive_CCMatcher> _action = _precondition.action(
        CRUDActivationStateEnum.CREATED, _function);
      final IMatchProcessor<PackageToFolderRule_Recursive_CCMatch> _function_1 = (PackageToFolderRule_Recursive_CCMatch it) -> {
        this.tggRuntimeUtil.removeOperationalRuleMatch(it);
      };
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_CCMatch, PackageToFolderRule_Recursive_CCMatcher> _action_1 = _action.action(CRUDActivationStateEnum.DELETED, _function_1);
      ActivationLifeCycle _default = Lifecycles.getDefault(false, true);
      EventDrivenTransformationRuleFactory.EventDrivenTransformationRuleBuilder<PackageToFolderRule_Recursive_CCMatch, PackageToFolderRule_Recursive_CCMatcher> _addLifeCycle = _action_1.addLifeCycle(_default);
      return _addLifeCycle.build();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
