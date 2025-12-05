package projeto.backend.service;

import org.springframework.stereotype.Service;
import projeto.backend.entity.Categoria;
import projeto.backend.dto.CategoriaRequestDto;
import projeto.backend.mapper.CategoriaMapper;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {

    private final List<Categoria> categorias = new ArrayList<>();
    private final CategoriaMapper mapper;

    public CategoriaService(CategoriaMapper mapper) {
        this.mapper = mapper;
    }

    public Categoria criarCategoria(CategoriaRequestDto dto) {
        for (Categoria c : categorias) {
            if (c.getNome().equalsIgnoreCase(dto.getNome())) {
                throw new RuntimeException("Categoria já cadastrada");
            }
        }

        Categoria categoria = mapper.toEntity(dto);
        categoria.setId(categorias.size() + 1);
        categorias.add(categoria);
        return categoria;
    }

    public List<Categoria> listarCategorias() {
        return categorias;
    }

    public Categoria buscarPorId(Integer id) {
        return categorias.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    public Categoria atualizarCategoria(Integer id, CategoriaRequestDto dto) {
        Categoria categoria = buscarPorId(id);

        for (Categoria c : categorias) {
            if (!c.getId().equals(id) && c.getNome().equalsIgnoreCase(dto.getNome())) {
                throw new RuntimeException("Nome de categoria já existe");
            }
        }

        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        return categoria;
    }

    public void deletarCategoria(Integer id) {
        Categoria categoria = buscarPorId(id);

        if (!categoria.getLivros().isEmpty()) {
            throw new RuntimeException("Não é possível excluir categoria com livros associados");
        }

        categorias.remove(categoria);
    }
}