package br.com.caelum.ingresso.controller;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Carrinho;
import br.com.caelum.ingresso.model.ImagemCapa;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.TipoDeIngresso;
import br.com.caelum.ingresso.model.form.SessaoForm;
import br.com.caelum.ingresso.rest.ImdbClient;
import br.com.caelum.ingresso.validacao.GerenciadorDeSessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class SessaoController {

    @Autowired
    private SalaDao salaDao;

    @Autowired
    private FilmeDao filmeDao;

    @Autowired
    private SessaoDao sessaoDao;

    @Autowired
    private ImdbClient imdbClient;

    @Autowired
    private Carrinho carrinho;


    public SessaoController(SalaDao salaDao, FilmeDao filmeDao, SessaoDao sessaoDao, ImdbClient imdbClient, Carrinho carrinho){
        this.salaDao = salaDao;
        this.filmeDao = filmeDao;
        this.sessaoDao = sessaoDao;
        this.imdbClient = imdbClient;
        this.carrinho = carrinho;
    }

    @GetMapping("/sessao/{id}/lugares")
    public ModelAndView lugaresNaSessao(@PathVariable("id") Integer sessaoId) {
        ModelAndView modelAndView = new ModelAndView("sessao/lugares");
        Sessao sessao = sessaoDao.findOne(sessaoId);
        Optional<ImagemCapa> imagemCapa = imdbClient.request(sessao.getFilme(), ImagemCapa.class);

        modelAndView.addObject("sessao", sessao);
        modelAndView.addObject("carrinho", carrinho);
        modelAndView.addObject("imagemCapa", imagemCapa.orElse(new ImagemCapa()));
        modelAndView.addObject("tiposDeIngressos", TipoDeIngresso.values());

        return modelAndView;
    }

    @GetMapping("/admin/sessao")
    public ModelAndView form(@RequestParam("salaId") Integer salaId, SessaoForm form) {
        form.setSalaId(salaId);
        ModelAndView modelAndView = new ModelAndView("sessao/sessao");

        modelAndView.addObject("sala", salaDao.findOne(salaId));
        modelAndView.addObject("filmes", filmeDao.findAll());
        modelAndView.addObject("form", form);

        return modelAndView;
    }

    @PostMapping("/admin/sessao")
    @Transactional
    public ModelAndView salva(@Valid SessaoForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return form(form.getSalaId(),form);
        Sessao sessao = form.toSessao(salaDao, filmeDao);
        List<Sessao> sessoesDaSala = sessaoDao.buscaSessoesDaSala(sessao.getSala());
        GerenciadorDeSessao gerenciadorDeSessao = new GerenciadorDeSessao(sessoesDaSala);
        if(gerenciadorDeSessao.cabe(sessao)) {
            sessaoDao.save(sessao);
            return new ModelAndView("redirect:/admin/sala/" +  form.getSalaId() + "/sessoes");
        }

        return form(form.getSalaId(), form);
    }
}
