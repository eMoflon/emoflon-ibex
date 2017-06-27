package org.benchmarx.database.core;

import Database.Column;
import Database.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.benchmarx.database.core.ColumnNormalizer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class TableNormalizer implements Comparator<Table> {
  private ColumnNormalizer columnNormalizer;
  
  public TableNormalizer() {
    ColumnNormalizer _columnNormalizer = new ColumnNormalizer();
    this.columnNormalizer = _columnNormalizer;
  }
  
  @Override
  public int compare(final Table table1, final Table table2) {
    final String tableString1 = this.stringify(table1);
    final String tableString2 = this.stringify(table2);
    return tableString1.compareTo(tableString2);
  }
  
  public void normalize(final List<Table> tables) {
    Collections.<Table>sort(tables, this);
  }
  
  public String stringify(final Table tables) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Table {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("heading = \"table\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("columns = [");
    _builder.newLine();
    _builder.append("\t");
    EList<Column> _columns = tables.getColumns();
    final List<Column> sortedList = new ArrayList<Column>(_columns);
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    this.columnNormalizer.normalize(sortedList);
    _builder.newLineIfNotEmpty();
    {
      boolean _hasElements = false;
      for(final Column c : sortedList) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(", ", "\t");
        }
        _builder.append("\t");
        String _stringify = this.columnNormalizer.stringify(c);
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
