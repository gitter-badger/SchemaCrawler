/*
 *
 * SchemaCrawler
 * http://www.schemacrawler.com
 * Copyright (c) 2000-2015, Sualeh Fatehi.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 */
package schemacrawler.tools.linter;


import java.util.Collection;

import schemacrawler.schema.Index;
import schemacrawler.schema.Table;
import schemacrawler.schema.View;
import schemacrawler.tools.lint.BaseLinter;

public class LinterTableWithNoIndexes
  extends BaseLinter
{

  @Override
  public String getSummary()
  {
    return "no indexes";
  }

  @Override
  protected void lint(final Table table)
  {
    if (table != null && !(table instanceof View))
    {
      final Collection<Index> indexes = table.getIndexes();
      if (table.getPrimaryKey() == null && indexes.isEmpty())
      {
        addLint(table, getSummary(), true);
      }
    }
  }

}
