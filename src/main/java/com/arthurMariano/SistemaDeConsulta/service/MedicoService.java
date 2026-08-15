package com.arthurMariano.SistemaDeConsulta.service;

import com.arthurMariano.SistemaDeConsulta.dto.MedicoPesquisaDto;
import com.arthurMariano.SistemaDeConsulta.dto.RegistroResponse;
import com.arthurMariano.SistemaDeConsulta.exception.DadoJaCadastradoException;
import com.arthurMariano.SistemaDeConsulta.exception.NaoAchadoException;
import com.arthurMariano.SistemaDeConsulta.forms.MedicoForm;
import com.arthurMariano.SistemaDeConsulta.mapper.MedicoMapper;
import com.arthurMariano.SistemaDeConsulta.model.Especialidade;
import com.arthurMariano.SistemaDeConsulta.model.Medico;
import com.arthurMariano.SistemaDeConsulta.model.enums.Cargo;
import com.arthurMariano.SistemaDeConsulta.repository.EspecialidadeRepository;
import com.arthurMariano.SistemaDeConsulta.repository.MedicoRepository;
import com.arthurMariano.SistemaDeConsulta.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MedicoService {
    private final MedicoRepository medicoRepository;
    private final MedicoMapper medicoMapper;
    private final PasswordEncoder codificadorDeSenha;
    private final UsuarioRepository usuarioRepository;
    private final EspecialidadeRepository especialidadeRepository;


    public List<MedicoPesquisaDto> procurarPorEspecialidade(Long id) {
        List<Medico> medicos = medicoRepository.findByEspecialidadeId(id);
        if (medicos.isEmpty()) throw new NaoAchadoException("medico", "Nenhum Medico com essa especialidade");
        return medicoMapper.medicoParaMedicoPesquisaDTO(medicos);


    }

    public RegistroResponse cadastrarMedico(MedicoForm medicoForm) {
        if (usuarioRepository.existsByLogin(medicoForm.login())) {

            throw new DadoJaCadastradoException("Login", "Esse Login ja foi usado");
        }
        Especialidade especialidade = especialidadeRepository.findById(medicoForm.especialidadeID()).orElseThrow(() -> new NaoAchadoException("especialidade", "especialidade não existe"));
        Medico medico = medicoMapper.medicoFormParaMedico(medicoForm);
        medico.setEspecialidade(especialidade);
        medico.setCargo(medicoForm.admin() ? Cargo.ADMIN : Cargo.MEDICO);
        String senha = medico.getSenha();
        String encode = codificadorDeSenha.encode(senha);
        medico.setSenha(encode);
        Medico save = medicoRepository.save(medico);
        return new RegistroResponse(save.getId(), save.getLogin());
    }

    public List<MedicoPesquisaDto> pesquisarMedicoPorNome(String nome) {
        List<Medico> medicos = medicoRepository.findByNomeContainingIgnoreCase(nome);
        return medicoMapper.medicoParaMedicoPesquisaDTO(medicos);

    }
}
