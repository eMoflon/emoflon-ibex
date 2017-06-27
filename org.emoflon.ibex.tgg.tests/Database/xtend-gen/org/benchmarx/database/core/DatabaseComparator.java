package org.benchmarx.database.core;

import Database.DB;
import Database.Table;
import java.util.ArrayList;
import java.util.List;
import org.benchmarx.database.core.TableNormalizer;
import org.benchmarx.emf.Comparator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.junit.Assert;

@SuppressWarnings("all")
public class DatabaseComparator implements Comparator<DB> {
  private TableNormalizer tableNormalizer;
  
  public DatabaseComparator() {
    TableNormalizer _tableNormalizer = new TableNormalizer();
    this.tableNormalizer = _tableNormalizer;
  }
  
  @Override
  public void assertEquals(final DB expected, final DB actual) {
    Assert.assertTrue(this.stringify(expected).startsWith("DB"));
    Assert.assertEquals(this.stringify(expected), this.stringify(actual));
  }
  
  public String stringify(final DB db) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("DB {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("title = \"database\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("tables = [");
    _builder.newLine();
    _builder.append("\t");
    EList<Table> _tables = db.getTables();
    final List<Table> sortedList = new ArrayList<Table>(_tables);
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    this.tableNormalizer.normalize(sortedList);
    _builder.newLineIfNotEmpty();
    {
      boolean _hasElements = false;
      for(final Table c : sortedList) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(", ", "\t");
        }
        _builder.append("\t");
        String _stringify = this.tableNormalizer.stringify(c);
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
