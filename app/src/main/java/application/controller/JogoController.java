package application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import application.model.Jogo;
import application.repository.JogoRepository;

@RestController
@RequestMapping("/jogos")
public class JogoController {

    @Autowired
    private JogoRepository jogoRepo;


    @GetMapping
    public Iterable<Jogo> getJogos(){
        return jogoRepo.findAll();
    }


    @GetMapping("/{id}")
    public Jogo getJogo(@PathVariable Long id){
        Optional<Jogo> resultado = jogoRepo.findById(id);

        if(resultado.isEmpty()){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Jogo não encontrada"
            );
        };
        return resultado.get();
    }

    @PostMapping
    public Jogo postJogo(@RequestBody Jogo jogo){
        return jogoRepo.save(jogo);
    }

    @PutMapping("/{id}")
    public Jogo atualizarJogo(@PathVariable Long id, @RequestBody Jogo body){
        Optional<Jogo> resultado = jogoRepo.findById(id);

        if(resultado.isEmpty()){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Jogo não encontrada"
            );
        };

        if(body.getNomeJogo().isEmpty()){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Campo Nome do Jogo é obrigatório"
            );
        };

        if(body.getGenero().isEmpty()){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Campo gênero é obrigatório"
            );
        };
        

        resultado.get().setNomeJogo(body.getNomeJogo());
        resultado.get().setGenero(body.getGenero());
        return jogoRepo.save(resultado.get());
    }

    @DeleteMapping("/{id}")
    public void deleteJogo(@PathVariable Long id){;
        if(!jogoRepo.existsById(id)){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Jogo não encontrada"
            );
        }
        jogoRepo.deleteById(id);
    }
    
}
