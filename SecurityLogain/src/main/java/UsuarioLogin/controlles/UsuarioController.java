package UsuarioLogin.controlles;

import UsuarioLogin.entitys.UsuarioEntity;
import UsuarioLogin.repositorys.UsuarioRepository;
import UsuarioLogin.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/admin")
    public String adminRestrito() {
        return "Apenas administradores têm acesso a este recurso.";
    }

    @GetMapping("/usuario")
    public String userRestrito() {
        return "Apenas usuários têm acesso a este recurso.";
    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioEntity usuario) {
        System.out.println(usuario.getUsername());
        try{
            System.out.println("entrou");
            usuario.setSenha(passwordEncoder.encode(usuario.getPassword()));
            System.out.println(usuario.getUsername());
            usuarioRepository.save(usuario);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<List<UsuarioEntity>> listarTodosUsuarios() {
        List<UsuarioEntity> usauarios = usuarioService.listarTodosUsuarios();
        return ResponseEntity.ok(usauarios);
    }
}
