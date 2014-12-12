package schemacrawler.integration.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static schemacrawler.test.utility.TestUtility.compareOutput;
import static sf.util.Utility.UTF8;

import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import schemacrawler.schema.Catalog;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.server.hsqldb.HyperSQLDatabaseConnector;
import schemacrawler.test.utility.BaseDatabaseTest;
import schemacrawler.tools.databaseconnector.DatabaseConnectorRegistry;
import schemacrawler.tools.options.InfoLevel;
import schemacrawler.tools.options.OutputFormat;
import schemacrawler.tools.options.TextOutputFormat;
import schemacrawler.utility.SchemaCrawlerUtility;

public class TestBundledDistributions
  extends BaseDatabaseTest
{

  @Test
  public void testHsqldbMain()
    throws Exception
  {

    final File testConfigFile = File.createTempFile("schemacrawler.test.",
                                                    ".properties");
    try (final Writer writer = new PrintWriter(testConfigFile, UTF8.name());)
    {
      final Properties properties = new Properties();
      properties
        .setProperty("hsqldb.tables",
                     "SELECT TABLE_CAT, TABLE_SCHEM, TABLE_NAME, TABLE_TYPE, REMARKS FROM INFORMATION_SCHEMA.SYSTEM_TABLES");
      properties.store(writer, "testHsqldbMain");
    }

    final OutputFormat outputFormat = TextOutputFormat.text;
    final String referenceFile = "hsqldb.main" + "." + outputFormat.getFormat();
    final File testOutputFile = File.createTempFile("schemacrawler."
                                                        + referenceFile + ".",
                                                    ".test");
    testOutputFile.delete();

    schemacrawler.Main.main(new String[] {
        "-server=hsqldb",
        "-database=schemacrawler",
        "-user=sa",
        "-password=",
        "-g",
        testConfigFile.getAbsolutePath(),
        "-command=details,dump,count,hsqldb.tables",
        "-infolevel=maximum",
        "-outputfile=" + testOutputFile
    });

    final List<String> failures = compareOutput(referenceFile,
                                                testOutputFile,
                                                outputFormat.getFormat());
    if (failures.size() > 0)
    {
      fail(failures.toString());
    }
    else
    {
      testConfigFile.delete();
      testOutputFile.delete();
    }

  }

  @Test
  public void testHsqldbWithConnection()
    throws Exception
  {

    final SchemaCrawlerOptions schemaCrawlerOptions = new HyperSQLDatabaseConnector()
      .getDatabaseSystemConnector().getSchemaCrawlerOptions(InfoLevel.maximum);
    final Catalog catalog = SchemaCrawlerUtility
      .getCatalog(getConnection(), schemaCrawlerOptions);
    assertNotNull(catalog);

    assertEquals(6, catalog.getSchemas().size());
    final Schema schema = catalog.getSchema("PUBLIC.BOOKS");
    assertNotNull(schema);

    assertEquals(6, catalog.getTables(schema).size());
    final Table table = catalog.getTable(schema, "AUTHORS");
    assertNotNull(table);

    assertEquals(1, table.getTriggers().size());
    assertNotNull(table.getTrigger("TRG_AUTHORS"));

  }

  @Test
  public void testPlugin_hsqldb()
    throws Exception
  {
    final DatabaseConnectorRegistry registry = new DatabaseConnectorRegistry();
    assertTrue(registry.hasDatabaseSystemIdentifier("hsqldb"));
  }

}
