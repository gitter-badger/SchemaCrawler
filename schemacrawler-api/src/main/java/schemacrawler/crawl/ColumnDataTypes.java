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
package schemacrawler.crawl;


import schemacrawler.schema.SchemaReference;

class ColumnDataTypes
  extends NamedObjectList<MutableColumnDataType>
{

  private static final long serialVersionUID = 6793135093651666453L;

  MutableColumnDataType lookupColumnDataTypeByType(final int type)
  {
    final SchemaReference systemSchema = new SchemaReference();
    MutableColumnDataType columnDataType = null;
    for (final MutableColumnDataType currentColumnDataType: this)
    {
      if (type == currentColumnDataType.getJavaSqlType().getJavaSqlType())
      {
        columnDataType = currentColumnDataType;
        if (columnDataType.getSchema().equals(systemSchema))
        {
          break;
        }
      }
    }
    return columnDataType;
  }

}
