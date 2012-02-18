/*
 * Image/J Plugins
 * Copyright (C) 2002-2012 Jarek Sacha
 * Author's email: jsacha at users dot sourceforge dot net
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Latest release available at http://sourceforge.net/projects/ij-plugins/
 */

package net.sf.ij_plugins.scala.console.editor

import net.sf.ij_plugins.scala.console
import java.awt.{Font, Color}
import org.fife.ui.rsyntaxtextarea.{SyntaxScheme, Token, SyntaxConstants, RSyntaxTextArea}
import org.fife.ui.rtextarea.RTextScrollPane
import swing.{BorderPanel, Component}


/**
 * The editor view, following the MVC pattern. Wrapper for `RSyntaxTextArea`.
 *
 * @author Jarek Sacha
 * @since 2/17/12 6:07 PM
 */
private[editor] class EditorView(private val textArea: RSyntaxTextArea) extends BorderPanel {

    // Put text area in the center of the panel.
    layout(Component.wrap(new RTextScrollPane(textArea))) = BorderPanel.Position.Center

    init()


    private def init() {
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SCALA)

        setFont(textArea, console.defaultEditorFont)
        textArea.setAntiAliasingEnabled(true)
        textArea.setFractionalFontMetricsEnabled(true)
        textArea.setHighlightCurrentLine(false)

        val scheme = textArea.getSyntaxScheme
        scheme.styles(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = new Color(0, 128, 0)
        scheme.styles(Token.RESERVED_WORD).font = console.defaultEditorFont.deriveFont(Font.BOLD)
        scheme.styles(Token.RESERVED_WORD).foreground = new Color(0, 0, 128)
        scheme.styles(Token.SEPARATOR).foreground = Color.GRAY
    }

    /**
     * Set the font for all token types.
     *
     * @param textArea The text area to modify.
     * @param font The font to use.
     */
    private def setFont(textArea: RSyntaxTextArea, font: Font) {
        if (font != null) {
            val ss = textArea.getSyntaxScheme.clone().asInstanceOf[SyntaxScheme]
            for (i <- 0 until ss.styles.length) {
                if (ss.styles(i) != null) {
                    ss.styles(i).font = font;
                }
            }
            textArea.setSyntaxScheme(ss);
            textArea.setFont(font);
        }
    }
}