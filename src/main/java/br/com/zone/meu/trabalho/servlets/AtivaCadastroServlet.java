package br.com.zone.meu.trabalho.servlets;

import br.com.zone.meu.trabalho.daos.TokenDAO;
import br.com.zone.meu.trabalho.daos.UsuarioDAO;
import br.com.zone.meu.trabalho.entidades.Token;
import br.com.zone.meu.trabalho.entidades.Usuario;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */
@WebServlet(urlPatterns = {"/activate"})
public class AtivaCadastroServlet extends HttpServlet {

    @Inject
    private TokenDAO tokenDAO;
    
    @Inject
    private UsuarioDAO usuarioDAO;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        
        Token token = tokenDAO.buscarTokenPorHash(req.getParameter("token"));
        
        if(token == null){
            
            req.setAttribute("mensagem", "O token informado não é válido");
            
        }else{
            
            Usuario usuario = token.getUsuario();
            usuario.setAtivo(Boolean.TRUE);
            
            try {
                
                usuarioDAO.salvar(usuario);
                
                tokenDAO.excluir(token);
                
            } catch (Exception ex) {
                Logger.getLogger(AtivaCadastroServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            req.setAttribute("mensagem", "Sua conta foi ativado com sucesso, "
                                       + "faça login para continuar");
            
        }
        
        try {
            req.getRequestDispatcher("login.xhtml").forward(req, resp);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(AtivaCadastroServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
