package de.vdheide.mp3;

public class ParserTest extends MP3TestCase {
	
	public void testParseEncoding() {
		byte[] testIso = { 0, 84, 101, 115, 116 };
		Parser parser = new Parser(testIso, true);
		assertEquals(Parser.ISO, parser.getEncoding());
		assertEquals(1, parser.getPosition());

		byte[] testUnicode = { 1, -2, -1, 0, 84, 0, 101, 0, 115, 0, 116 };
		parser = new Parser(testUnicode, true);
		assertEquals(Parser.UNICODE, parser.getEncoding());
		assertEquals(1, parser.getPosition());
	}

	public void testParseTextByteIso() throws Exception {
		byte[] test = { 84, 101, 115, 116 }; 
		Parser parser = new Parser(test, false);
		String out = parser.parseText(Parser.ISO);
		
		assertEquals("Test", out);
	}

	public void testParseTextByteUTF16BE() throws Exception {
		byte[] test = { -2, -1, 0, 84, 0, 101, 0, 115, 0, 116 }; 
		Parser parser = new Parser(test, false);
		String out = parser.parseText(Parser.UNICODE);
		
		assertEquals("Test", out);
	}

	public void testParseTextByteUTF16LE() throws Exception {
		byte[] test = { -1, -2, 84, 0, 101, 0, 115, 0, 116, 0 }; 
		Parser parser = new Parser(test, false);
		String out = parser.parseText(Parser.UNICODE);
		
		assertEquals("Test", out);
	}

	public void testParseTextByteUTF16BEMoreInput() throws Exception {
		byte[] test = { -2, -1, 0, 84, 0, 101, 0, 115, 0, 116, 0, 0, 1, 2 }; 
		Parser parser = new Parser(test, false);
		String out = parser.parseText(Parser.UNICODE);
		
		assertEquals("Test", out);
	}

	public void testParseTextByteUTF16LEMoreInput() throws Exception {
		byte[] test = { -1, -2, 84, 0, 101, 0, 115, 0, 116, 0, 0, 0, 1, 2 }; 
		Parser parser = new Parser(test, false);
		String out = parser.parseText(Parser.UNICODE);
		
		assertEquals("Test", out);
	}

	public void testParseBinary() throws Exception {
		byte[] test = { -1, -2, 84, 0, 101, 0, 115, 0, 116, 0, 0, 0, 1, 2 }; 
		Parser parser = new Parser(test, false);
		byte[] out = parser.parseBinary();
		
		assertEqualsByteArray(test, out);
	}

	public void testParseBinaryInt() throws Exception {
		byte[] test = { -1, -2, 84, 0, 101, 0, 115, 0, 116, 0, 0, 0, 1, 2 }; 
		Parser parser = new Parser(test, false);
		byte[] out = parser.parseBinary(6);
		
		byte[] expect = { -1, -2, 84, 0, 101, 0 };
		assertEqualsByteArray(expect, out);
	}

	public void testParseBinaryIntWithPosition() throws Exception {
		byte[] test = { -1, -2, 84, 0, 101, 0, 115, 0, 116, 0, 0, 0, 1, 2 }; 
		Parser parser = new Parser(test, false);
		parser.setPosition(2);
		byte[] out = parser.parseBinary(6);

		byte[] expect = { 84, 0, 101, 0, 115, 0 };
		assertEqualsByteArray(expect, out);
	}

}
