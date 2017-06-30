package org.benchmarx.classInheritanceHierarchy.core;

import ClassInheritanceHierarchy.ClassPackage;
import ClassInheritanceHierarchy.Clazz;
import java.util.ArrayList;
import java.util.List;
import org.benchmarx.classInheritanceHierarchy.core.ClazzNormalizer;
import org.benchmarx.emf.Comparator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.junit.Assert;

@SuppressWarnings("all")
public class ClassInheritanceHierarchyComparator implements Comparator<ClassPackage> {
  private ClazzNormalizer clazzNormalizer;
  
  public ClassInheritanceHierarchyComparator() {
    ClazzNormalizer _clazzNormalizer = new ClazzNormalizer();
    this.clazzNormalizer = _clazzNormalizer;
  }
  
  @Override
  public void assertEquals(final ClassPackage expected, final ClassPackage actual) {
    String _stringify = this.stringify(expected);
    boolean _startsWith = _stringify.startsWith("ClassPackage");
    Assert.assertTrue(_startsWith);
    String _stringify_1 = this.stringify(expected);
    String _stringify_2 = this.stringify(actual);
    Assert.assertEquals(_stringify_1, _stringify_2);
  }
  
  public String stringify(final ClassPackage classPackage) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ClassPackage {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("name = \"classPackage\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("classes = [");
    _builder.newLine();
    _builder.append("\t");
    EList<Clazz> _classes = classPackage.getClasses();
    final List<Clazz> sortedList = new ArrayList<Clazz>(_classes);
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    this.clazzNormalizer.normalize(sortedList);
    _builder.newLineIfNotEmpty();
    {
      boolean _hasElements = false;
      for(final Clazz c : sortedList) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(", ", "\t");
        }
        _builder.append("\t");
        String _stringify = this.clazzNormalizer.stringify(c);
        _builder.append(_stringify, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
}
