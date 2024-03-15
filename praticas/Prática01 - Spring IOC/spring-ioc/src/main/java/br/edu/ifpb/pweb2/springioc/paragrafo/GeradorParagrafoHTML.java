package br.edu.ifpb.pweb2.springioc.paragrafo;

import org.springframework.stereotype.Component;

@Component
public class GeradorParagrafoHTML implements GeradorParagrafoIntf{
    @Override
    public void addParagrafo(String texto) {
        System.out.println("<p> " + texto + "</p>");
    }
}
