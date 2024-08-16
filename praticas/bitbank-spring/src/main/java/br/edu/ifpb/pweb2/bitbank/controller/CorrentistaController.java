package br.edu.ifpb.pweb2.bitbank.controller;

import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import br.edu.ifpb.pweb2.bitbank.service.CorrentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/correntistas")
public class CorrentistaController {

    @Autowired
    private CorrentistaService correntistaService;


    @RequestMapping("/form")
    public String getForm(Correntista correntista, ModelAndView model) {
        model.addObject("correntista", correntista);
        return "correntistas/form";
    }

    @PostMapping
    public ModelAndView save(Correntista correntista, ModelAndView model, RedirectAttributes attr) {
        String operacao = (correntista.getId() == null) ? "criada" : "salva";
        correntistaService.save(correntista);
        attr.addFlashAttribute("mensagem", "Conta " + operacao + " com sucesso!");
        model.setViewName("redirect:correntistas");
        return model;
    }

    @GetMapping
    public ModelAndView list(ModelAndView model) {
        model.addObject("correntistas", correntistaService.findAll());
        model.setViewName("correntistas/list");
        return model;
    }

    @RequestMapping("/{id}")
    public String getCorrentistaById(@PathVariable(value = "id") Integer id, ModelAndView model) {
        model.addObject("correntista", correntistaService.findById(id));
        return "correntistas/form";
    }
}
