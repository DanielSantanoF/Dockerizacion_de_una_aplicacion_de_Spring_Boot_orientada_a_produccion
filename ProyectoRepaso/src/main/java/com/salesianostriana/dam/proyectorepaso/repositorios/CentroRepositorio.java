/**
 * 
 */
package com.salesianostriana.dam.proyectorepaso.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.proyectorepaso.model.Centro;

/**
 * @author jallamas
 *
 */
@Repository
public interface CentroRepositorio extends JpaRepository<Centro, Long>{

}
