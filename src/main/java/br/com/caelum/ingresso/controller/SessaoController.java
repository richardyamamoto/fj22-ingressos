package br.com.caelum.ingresso.controller;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.form.SessaoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class SessaoController {

    @Autowired
    private SalaDao salaDao;

    @Autowired
    private FilmeDao filmeDao;

    @Autowired
    private SessaoDao sessaoDao;


    @GetMapping("/admin/sessao")
    public ModelAndView form(@RequestParam("salaId") Integer salaId, SessaoForm sessaoForm) {
        sessaoForm.setSalaId(salaId);
        ModelAndView modelAndView = new ModelAndView("sessao/sessao");
        modelAndView.addObject("sala", salaDao.findOne(salaId));
        modelAndView.addObject("filmes", filmeDao.findAll());
        modelAndView.addObject("sessaoForm", sessaoForm);
        return modelAndView;
    }

    @PostMapping("/admin/sessao")
    @Transactional
    public ModelAndView salva(@Valid SessaoForm sessaoForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return form(sessaoForm.getSalaId(),sessaoForm);
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/sala/" +  sessaoForm.getSalaId() + "/sessoes");
        Sessao sessao = sessaoForm.toSessao(salaDao, filmeDao);
        sessaoDao.save(sessao);
        return modelAndView;
    }
}
