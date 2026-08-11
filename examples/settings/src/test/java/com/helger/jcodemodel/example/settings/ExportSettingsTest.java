package com.helger.jcodemodel.example.settings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;
import com.helger.jcodemodel.writer.FormatterSettings;

/// actually not a test, but export the settings at the project root so people
/// have an idea what settings are available.
public class ExportSettingsTest {

  @Test
  public void writeYaml() {
    FormatterSettings export = new FormatterSettings();

    YAMLFactory f =
        new YAMLFactory()
            .disable(Feature.WRITE_DOC_START_MARKER);
    ObjectMapper om = new ObjectMapper(f);

    File out = new File("example-settings.yaml");
    try (FileWriter writer = new FileWriter(out)) {
      om.writer().writeValue(writer, export);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void writeJson() {
    FormatterSettings export = new FormatterSettings();

    DefaultPrettyPrinter prettyPrinter =
        new DefaultPrettyPrinter()
        .withObjectIndenter(new DefaultIndenter().withLinefeed("\n"));
    ObjectMapper om =
        new ObjectMapper()
            .setDefaultPrettyPrinter(prettyPrinter)
            .enable(SerializationFeature.INDENT_OUTPUT);

    File out = new File("example-settings.json");
    try (FileWriter writer = new FileWriter(out)) {
      om.writer().writeValue(writer, export);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
