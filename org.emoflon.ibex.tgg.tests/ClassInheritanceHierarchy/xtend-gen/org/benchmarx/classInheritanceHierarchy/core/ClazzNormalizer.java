package org.benchmarx.classInheritanceHierarchy.core;

import ClassInheritanceHierarchy.Attribute;
import ClassInheritanceHierarchy.Clazz;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.benchmarx.classInheritanceHierarchy.core.AttributeNormalizer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class ClazzNormalizer implements Comparator<Clazz> {
  private AttributeNormalizer attributeNormalizer;
  
  public ClazzNormalizer() {
    AttributeNormalizer _attributeNormalizer = new AttributeNormalizer();
    this.attributeNormalizer = _attributeNormalizer;
  }
  
  @Override
  public int compare(final Clazz clazz1, final Clazz clazz2) {
    final String clazzString1 = this.stringify(clazz1);
    final String clazzString2 = this.stringify(clazz2);
    return clazzString1.compareTo(clazzString2);
  }
  
  public String stringify(final Clazz clazz) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Clazz {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("name = \"clazz\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("superClass = \"");
    {
      Clazz _superClass = clazz.getSuperClass();
      boolean _tripleNotEquals = (_superClass != null);
      if (_tripleNotEquals) {
        _builder.append("superclass");
      }
    }
    _builder.append("\",");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("attributes = [");
    _builder.newLine();
    _builder.append("\t");
    EList<Attribute> _attributes = clazz.getAttributes();
    final List<Attribute> sortedList = new ArrayList<Attribute>(_attributes);
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    this.attributeNormalizer.normalize(sortedList);
    _builder.newLineIfNotEmpty();
    {
      boolean _hasElements = false;
      for(final Attribute a : sortedList) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(", ", "\t");
        }
        _builder.append("\t");
        String _stringify = this.attributeNormalizer.stringify(a);
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
  
  public void normalize(final List<Clazz> clazzes) {
    Collections.<Clazz>sort(clazzes, this);
  }
}
