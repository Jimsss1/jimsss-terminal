package jimsss.terminal.plugin;

import jimsss.terminal.MetaData;
import jimsss.terminal.i18n.I18n;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteExampleXml {
    public WriteExampleXml() throws IOException {
        FileWriter fileWriter = new FileWriter(MetaData.PLUGIN_XML_FILE);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        bufferedWriter.newLine();
        bufferedWriter.write("<plugins>");
        bufferedWriter.newLine();
        bufferedWriter.write("    <!--");
        bufferedWriter.newLine();
        bufferedWriter.write("    <plugin>");
        bufferedWriter.newLine();
        bufferedWriter.write("        <jarPath>" + I18n.getString("example_xml_content.jar_path") + "</jarPath>");
        bufferedWriter.newLine();
        bufferedWriter.write("        <class>" + I18n.getString("example_xml_content.class") + "</class>");
        bufferedWriter.newLine();
        bufferedWriter.write("    </plugin>");
        bufferedWriter.newLine();
        bufferedWriter.write("    !-->");
        bufferedWriter.newLine();
        bufferedWriter.write("</plugins>");
        bufferedWriter.close();
        fileWriter.close();
    }
}
