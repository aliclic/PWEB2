package br.edu.ifpb.pweb2.bitbank.controller;

import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import br.edu.ifpb.pweb2.bitbank.service.CorrentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/correntistas")
public class CorrentistaController {

    @Autowired
    private CorrentistaService correntistaService;

    @RequestMapping("/form")
    public String getForm(Correntista correntista, Model model) {
        model.addAttribute("correntista", correntista);
        return "correntistas/form";
    }

    @RequestMapping("/save")
    public String save(Correntista correntista, Model model) {
        String nome = correntista.getNome();

        if (nome.length() > 50) {
            model.addAttribute("mensagem", "Erro: nome tem que ser menor que 50 caracteres");
            return "correntistas/form";
        }

        correntistaService.save(correntista);
        model.addAttribute("correntistas", correntistaService.findAll());
        return "correntistas/list";
    }

    @RequestMapping("/list")
    public String listAll(Model model) {
        model.addAttribute("correntistas", correntistaService.findAll());
        return "correntistas/list";
    }

    @RequestMapping("{id}")
    public String getCorrentistaById(@PathVariable(value = "id") Integer id, Model model) {
        model.addAttribute("correntista", correntistaService.findById(id));
        return "correntistas/form";
    }

}
