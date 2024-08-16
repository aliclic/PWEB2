package br.edu.ifpb.pweb2.bitbank.controller;

import br.edu.ifpb.pweb2.bitbank.model.Conta;
import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.model.Transacao;
import br.edu.ifpb.pweb2.bitbank.service.ContaService;
import br.edu.ifpb.pweb2.bitbank.service.CorrentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @Autowired
    private CorrentistaService correntistaService;

    @RequestMapping("/form")
    public ModelAndView getForm(ModelAndView modelAndView) {
        modelAndView.setViewName("contas/form");
        modelAndView.addObject("conta", new Conta(new Correntista()));
        return modelAndView;
    }

    @ModelAttribute("correntistaItems")
    public List<Correntista> getCorrentistas() {
        return correntistaService.findAll();
    }

    @PostMapping
    public ModelAndView saveConta(Conta conta, ModelAndView modelAndView, RedirectAttributes attr) {
        String operacao = (conta.getId() == null) ? "criada" : "salva";
        contaService.save(conta);
        modelAndView.addObject("contas", contaService.findAll());
        modelAndView.setViewName("redirect:contas");
        attr.addFlashAttribute("mensagem", "Conta "+operacao+" com sucesso!");
        return modelAndView;
    }

    @GetMapping
    public ModelAndView listAll(ModelAndView modelAndView) {
        modelAndView.setViewName("contas/list");
        modelAndView.addObject("contas", contaService.findAll());
        return modelAndView;
    }

    @RequestMapping("/{id}")
    public ModelAndView getCorrentistaById(@PathVariable(value = "id") Integer id, ModelAndView model) {
        model.addObject("conta", contaService.findById(id));
        model.setViewName("contas/form");
        return model;
    }

    @RequestMapping("/nuconta")
    public String getNuConta() {
        return "contas/operacao";
    }

    @RequestMapping(value = "/operacao")
    public String operacaoConta(String nuConta, Transacao transacao, ModelAndView model) {
        String proxPagina = "";
        if (nuConta != null && transacao.getValor() == null) {
            Conta conta = contaService.findByNumeroWithTransacoes(nuConta);
            if (conta != null) {
                model.addObject("conta", conta);
                model.addObject("transacao", transacao);
                proxPagina = "contas/operacao";
            } else {
                model.addObject("mensagem", "Conta inexistente!");
                proxPagina = "contas/operacao";
            }
        } else {
            Conta conta = contaService.findByNumeroWithTransacoes(nuConta);
            conta.addTransacao(transacao);
            contaService.save(conta);
            proxPagina = "redirect:/contas/"+conta.getId()+"/transacoes";
        }
        return proxPagina;
    }

    @RequestMapping(value = "/{id}/transacoes")
    public ModelAndView addTransacaoConta(@PathVariable("id") Integer idConta, ModelAndView model) {
        Conta conta = contaService.findByIdWithTransacoes(idConta);
        model.addObject("conta", conta);
        model.setViewName("contas/transacoes");
        return model;
    }

}

