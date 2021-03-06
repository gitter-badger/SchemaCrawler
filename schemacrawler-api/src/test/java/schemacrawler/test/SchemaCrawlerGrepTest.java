/*
 * SchemaCrawler
 * Copyright (c) 2000-2015, Sualeh Fatehi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package schemacrawler.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;

import schemacrawler.schema.Catalog;
import schemacrawler.schema.Column;
import schemacrawler.schema.Routine;
import schemacrawler.schema.RoutineColumn;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.RegularExpressionInclusionRule;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.test.utility.BaseDatabaseTest;
import schemacrawler.test.utility.TestName;
import schemacrawler.test.utility.TestWriter;

public class SchemaCrawlerGrepTest
  extends BaseDatabaseTest
{

  @Rule
  public TestName testName = new TestName();

  @Test
  public void grepColumns()
    throws Exception
  {
    try (final TestWriter out = new TestWriter("text");)
    {
      final SchemaCrawlerOptions schemaCrawlerOptions = new SchemaCrawlerOptions();
      schemaCrawlerOptions
        .setGrepColumnInclusionRule(new RegularExpressionInclusionRule(".*\\..*\\.BOOKID"));

      final Catalog catalog = getCatalog(schemaCrawlerOptions);
      final Schema[] schemas = catalog.getSchemas().toArray(new Schema[0]);
      assertEquals("Schema count does not match", 6, schemas.length);
      for (final Schema schema: schemas)
      {
        out.println("schema: " + schema.getFullName());
        final Table[] tables = catalog.getTables(schema).toArray(new Table[0]);
        for (final Table table: tables)
        {
          out.println("  table: " + table.getFullName());
          final Column[] columns = table.getColumns().toArray(new Column[0]);
          Arrays.sort(columns);
          for (final Column column: columns)
          {
            out.println("    column: " + column.getFullName());
          }
        }
      }

      out.assertEquals(testName.currentMethodFullName());
    }
  }

  @Test
  public void grepColumnsAndIncludeChildTables()
    throws Exception
  {

    final SchemaCrawlerOptions schemaCrawlerOptions = new SchemaCrawlerOptions();
    schemaCrawlerOptions
      .setGrepColumnInclusionRule(new RegularExpressionInclusionRule(".*\\.BOOKAUTHORS\\..*"));

    Catalog catalog;
    Schema schema;
    Table table;

    catalog = getCatalog(schemaCrawlerOptions);
    schema = catalog.lookupSchema("PUBLIC.BOOKS").get();
    assertNotNull("Schema PUBLIC.BOOKS not found", schema);
    assertEquals(1, catalog.getTables(schema).size());
    table = catalog.lookupTable(schema, "BOOKAUTHORS").get();
    assertNotNull("Table BOOKAUTHORS not found", table);

    schemaCrawlerOptions.setParentTableFilterDepth(1);
    catalog = getCatalog(schemaCrawlerOptions);
    schema = catalog.lookupSchema("PUBLIC.BOOKS").get();
    assertNotNull("Schema PUBLIC.BOOKS not found", schema);
    assertEquals(3, catalog.getTables(schema).size());
    table = catalog.lookupTable(schema, "BOOKAUTHORS").get();
    assertNotNull("Table BOOKAUTHORS not found", table);
    table = catalog.lookupTable(schema, "BOOKS").get();
    assertNotNull("Table BOOKS not found", table);
    table = catalog.lookupTable(schema, "AUTHORS").get();
    assertNotNull("Table AUTHORS not found", table);

  }

  @Test
  public void grepCombined()
    throws Exception
  {
    try (final TestWriter out = new TestWriter("text");)
    {
      final SchemaCrawlerOptions schemaCrawlerOptions = new SchemaCrawlerOptions();
      schemaCrawlerOptions
        .setGrepColumnInclusionRule(new RegularExpressionInclusionRule(".*\\..*\\.BOOKID"));
      schemaCrawlerOptions
        .setGrepDefinitionInclusionRule(new RegularExpressionInclusionRule(".*book author.*"));

      final Catalog catalog = getCatalog(schemaCrawlerOptions);
      final Schema[] schemas = catalog.getSchemas().toArray(new Schema[0]);
      assertEquals("Schema count does not match", 6, schemas.length);
      for (final Schema schema: schemas)
      {
        out.println("schema: " + schema.getFullName());
        final Table[] tables = catalog.getTables(schema).toArray(new Table[0]);
        for (final Table table: tables)
        {
          out.println("  table: " + table.getFullName());
          final Column[] columns = table.getColumns().toArray(new Column[0]);
          Arrays.sort(columns);
          for (final Column column: columns)
          {
            out.println("    column: " + column.getFullName());
          }
        }
      }

      out.assertEquals(testName.currentMethodFullName());
    }
  }

  @Test
  public void grepDefinitions()
    throws Exception
  {
    try (final TestWriter out = new TestWriter("text");)
    {
      final SchemaCrawlerOptions schemaCrawlerOptions = new SchemaCrawlerOptions();
      schemaCrawlerOptions
        .setGrepDefinitionInclusionRule(new RegularExpressionInclusionRule(".*book author.*"));

      final Catalog catalog = getCatalog(schemaCrawlerOptions);
      final Schema[] schemas = catalog.getSchemas().toArray(new Schema[0]);
      assertEquals("Schema count does not match", 6, schemas.length);
      for (final Schema schema: schemas)
      {
        out.println("schema: " + schema.getFullName());
        final Table[] tables = catalog.getTables(schema).toArray(new Table[0]);
        for (final Table table: tables)
        {
          out.println("  table: " + table.getFullName());
          final Column[] columns = table.getColumns().toArray(new Column[0]);
          Arrays.sort(columns);
          for (final Column column: columns)
          {
            out.println("    column: " + column.getFullName());
          }
        }
      }

      out.assertEquals(testName.currentMethodFullName());
    }
  }

  @Test
  public void grepProcedures()
    throws Exception
  {
    try (final TestWriter out = new TestWriter("text");)
    {
      final SchemaCrawlerOptions schemaCrawlerOptions = new SchemaCrawlerOptions();
      schemaCrawlerOptions
        .setGrepRoutineColumnInclusionRule(new RegularExpressionInclusionRule(".*\\.B_COUNT"));

      final Catalog catalog = getCatalog(schemaCrawlerOptions);
      final Schema[] schemas = catalog.getSchemas().toArray(new Schema[0]);
      assertEquals("Schema count does not match", 6, schemas.length);
      for (final Schema schema: schemas)
      {
        out.println("schema: " + schema.getFullName());
        final Routine[] routines = catalog.getRoutines(schema)
          .toArray(new Routine[0]);
        for (final Routine routine: routines)
        {
          out.println("  routine: " + routine.getFullName());
          final RoutineColumn[] columns = routine.getColumns()
            .toArray(new RoutineColumn[0]);
          for (final RoutineColumn column: columns)
          {
            out.println("    parameter: " + column.getFullName());
          }
        }
      }

      out.assertEquals(testName.currentMethodFullName());
    }

  }

}
