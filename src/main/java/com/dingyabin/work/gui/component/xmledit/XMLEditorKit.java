
package com.dingyabin.work.gui.component.xmledit;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class XMLEditorKit extends DefaultEditorKit implements XMLStyleConstants, KeyListener {
	private static final long serialVersionUID = 6303638967973333256L;

	public final static String ERROR_HIGHLIGHTING_ATTRIBUTE = "errorHighlighting";
	
	private boolean tagCompletion = false;
	private boolean autoIndent = false;

	private StyleContext context = null;
	private ViewFactory factory = null;

	/**
	 * Constructs an XMLEditorKit with view factory and Context, but with line
	 * wrapping turned off.
	 */
	public XMLEditorKit() {
		context = new StyleContext();
		
		setStyle(ELEMENT_NAME, new Color(136, 18, 128), Font.PLAIN);
		setStyle(ELEMENT_VALUE, Color.black, Font.PLAIN);
		setStyle(ELEMENT_PREFIX, new Color(136, 18, 128), Font.PLAIN);

		setStyle(ATTRIBUTE_NAME, new Color(153, 69, 0), Font.PLAIN);
		setStyle(ATTRIBUTE_VALUE, new Color(26, 26, 166), Font.PLAIN);
		setStyle(ATTRIBUTE_PREFIX, new Color(153, 69, 0), Font.PLAIN);

		setStyle(NAMESPACE_NAME, new Color(128, 128, 0), Font.PLAIN);
		setStyle(NAMESPACE_VALUE, new Color(63, 95, 191), Font.PLAIN);
		setStyle(NAMESPACE_PREFIX, new Color(128, 128, 0), Font.PLAIN);

		setStyle(ENTITY, new Color(102, 102, 102), Font.PLAIN);
		setStyle(CDATA, new Color(127, 159, 191), Font.PLAIN);
		setStyle(COMMENT, new Color(63, 127, 95), Font.PLAIN);
		setStyle(SPECIAL, new Color(102, 102, 102), Font.PLAIN);

		factory = new XMLViewFactory();
	}

	/**
	 * Get the MIME type of the data that this kit represents support for. This
	 * kit supports the type <code>text/xml</code>.
	 * 
	 * @return the type.
	 */
	public String getContentType() {
		return "text/xml";
	}

	/**
	 * Fetches the XML factory that can produce views for XML Documents.
	 * 
	 * @return the XML factory
	 */
	public ViewFactory getViewFactory() {
		return factory;
	}

	/**
	 * @param enabled true enables the tag completion
	 */
	public final void setTagCompletion(boolean enabled) {
		tagCompletion = enabled;
	}

	/**
	 * @param enabled true enables the auto indentation
	 */
	public final void setAutoIndentation(boolean enabled) {
		autoIndent = enabled;
	}

	/**
	 * Set the style identified by the name.
	 * 
	 * @param token
	 *            the style token
	 * @param foreground
	 *            the foreground color
	 * @param fontStyle
	 *            the font style Plain, Italic or Bold
	 */
	public void setStyle(String token, Color foreground, int fontStyle) {
		Style s = context.getStyle(token);
		
		if (s == null) {
			s = context.addStyle(token, context.new NamedStyle());
		}
			
		StyleConstants.setItalic(s, (fontStyle & Font.ITALIC) > 0);
		StyleConstants.setBold(s, (fontStyle & Font.BOLD) > 0);
		StyleConstants.setForeground(s, foreground);
		
	}
	
	@Override
	public void install(JEditorPane editor) {
		super.install(editor);

		editor.addKeyListener(this);
	}

	@Override
	public void deinstall(JEditorPane editor) {
		super.deinstall(editor);
		
		editor.removeKeyListener(this);
	}

	/**
	 * A simple view factory implementation.
	 */
	class XMLViewFactory implements ViewFactory {
		/**
		 * Creates the XML View.
		 * 
		 * @param elem
		 *            the root element.
		 * @return the XMLView
		 */
		public View create(Element elem) {
			try {
				return new XMLView(new XMLScanner(elem.getDocument()), context, elem);
			} catch (IOException e) {
				// Instead of an IOException, this will return null if the
				// XMLView could not be instantiated.
				// Should never happen.
			}

			return null;
		}
	}
	
	private static void completeTag(Document document, int off) throws BadLocationException {
		StringBuffer endTag = new StringBuffer();

		String text = document.getText(0, off);
		int startTag = text.lastIndexOf('<', off);
		int prefEndTag = text.lastIndexOf('>', off);

		// If there was a start tag and if the start tag is not empty
		// and if the start-tag has not got an end-tag already.
		if ((startTag > 0) && (startTag > prefEndTag) && (startTag < text.length() - 1)) {
			String tag = text.substring(startTag, text.length());
			char first = tag.charAt(1);

			if (first != '/' && first != '!' && first != '?' && !Character.isWhitespace(first)) {
				boolean finished = false;
				char previous = tag.charAt(tag.length() - 1);

				if (previous != '/' && previous != '-') {

					endTag.append("</");

					for (int i = 1; (i < tag.length()) && !finished; i++) {
						char ch = tag.charAt(i);

						if (!Character.isWhitespace(ch)) {
							endTag.append(ch);
						} else {
							finished = true;
						}
					}

					endTag.append(">");
				}
			}
		}

		document.insertString(off, endTag.toString(), null);
	}

	private static void autoIndent(Document document, int off) throws BadLocationException {
		StringBuffer newStr = new StringBuffer("\r\n");
		Element elem = document.getDefaultRootElement().getElement(document.getDefaultRootElement().getElementIndex(off));
		int start = elem.getStartOffset();
//		int end = elem.getEndOffset();
		String line = document.getText(start, off - start);

		boolean finished = false;

		for (int i = 0; (i < line.length()) && !finished; i++) {
			char ch = line.charAt(i);

			if (((ch != '\n') && (ch != '\f') && (ch != '\r')) && Character.isWhitespace(ch)) {
				newStr.append(ch);
			} else {
				finished = true;
			}
		}

		if (isStartElement(line)) {
			newStr.append("\t");
		}

		document.insertString(off, newStr.toString(), null);
	}
	
	// Tries to find out if the line finishes with an element start
	private static boolean isStartElement(String line) {
		boolean result = false;

		int first = line.lastIndexOf("<");
		int last = line.lastIndexOf(">");

		if (last < first) { // In the Tag
			result = true;
		} else {
			int firstEnd = line.lastIndexOf("</");
			int lastEnd = line.lastIndexOf("/>");

			// Last Tag is not an End Tag
			if ((firstEnd != first) && ((lastEnd + 1) != last)) {
				result = true;
			}
		}

		return result;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		JEditorPane editor = (JEditorPane)event.getSource();
		
		if (event.getKeyChar() == '>' && tagCompletion) {
			try {
				int pos = editor.getCaretPosition();
				completeTag(editor.getDocument(), pos);
				editor.setCaretPosition(pos);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		} else if (event.getKeyChar() == '\n' && autoIndent) {
			try {
				autoIndent(editor.getDocument(), editor.getCaretPosition());
				event.consume();
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent keyevent) {}

	@Override
	public void keyTyped(KeyEvent keyevent) {}
}