using _1_transfer_danych_z_wykorzystaniem_plikow_format_xml;

var xmlPath = Path.Combine("Assets", "data.xml");

Console.WriteLine("--- XML loaded by DOM Approach ---");
XmlReadWithDomApproach.Read(xmlPath);

Console.WriteLine("\n\n--- XML loaded by SAX Approach ---");
XmlReadWithSaxApproach.Read(xmlPath);

Console.WriteLine("\n\n--- XML loaded with XPath ---");
XmlReadWithXsltDom.Read(xmlPath);
