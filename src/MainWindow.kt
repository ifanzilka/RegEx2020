package ru.smak.regex

import ru.smak.regex.regex.RegexHelper
import java.awt.Color
import java.awt.Dimension
import javax.swing.*
import javax.swing.text.BadLocationException
import javax.swing.text.DefaultHighlighter

class MainWindow : JFrame(){

    private val textBlock: JEditorPane
    private val btnFind: JButton

    init{
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        minimumSize = Dimension(500, 500)
        textBlock = JEditorPane()
        btnFind = JButton()
        btnFind.text = "Найти число"
        btnFind.addActionListener { find() }
        val gl = GroupLayout(contentPane)
        layout = gl
        gl.setHorizontalGroup(
            gl.createSequentialGroup()
                .addGap(4)
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(textBlock, 450, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addComponent(btnFind, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                )
                .addGap(4)
        )
        gl.setVerticalGroup(
            gl.createSequentialGroup()
                .addGap(4)
                .addComponent(textBlock, 400, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addGap(4)
                .addComponent(btnFind, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(4)
        )
        pack()
    }

    private fun find() {
        val rh = RegexHelper()
        rh.regex = "[0-9]+"
        var txt = textBlock.text
        txt = txt.replace("\r", "")
        val result = rh.findIn(txt)
        val h = textBlock.highlighter
        val hp = DefaultHighlighter
            .DefaultHighlightPainter(Color.YELLOW)
        h.removeAllHighlights()
        for (res in result){
            try{
                h.addHighlight(res.first, res.second, hp)
            } catch (e: BadLocationException){}
        }
    }
}

