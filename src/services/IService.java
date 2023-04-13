/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import java.util.List;
import entities.Utilisateur;
/**
 *
 * @author Ranim
 */
public interface IService<T> {
    public void ajouter(T t);
    public List<T> afficher();
    public void supprimer(int id);
    public void modifier(T t);
    public boolean checkCredentials(String email, String password);
}
