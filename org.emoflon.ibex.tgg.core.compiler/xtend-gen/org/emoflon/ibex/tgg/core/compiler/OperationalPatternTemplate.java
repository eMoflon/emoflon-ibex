package org.emoflon.ibex.tgg.core.compiler;

import java.util.Collection;
import language.TGGRuleElement;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern;

@SuppressWarnings("all")
public class OperationalPatternTemplate {
  public String get(final Pattern pattern) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("pattern ");
    String _name = pattern.getName();
    _builder.append(_name, "");
    _builder.append("(");
    {
      Collection<TGGRuleElement> _signatureElements = pattern.getSignatureElements();
      boolean _hasElements = false;
      for(final TGGRuleElement e : _signatureElements) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(", ", "");
        }
        String _name_1 = e.getName();
        _builder.append(_name_1, "");
        _builder.append(":");
        String _typeOf = this.getTypeOf(e);
        _builder.append(_typeOf, "");
      }
    }
    _builder.append("){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    return _builder.toString();
  }
  
  public String getTypeOf(final TGGRuleElement el) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field type is undefined for the type TGGRuleNode");
  }
}
