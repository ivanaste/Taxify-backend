package com.kts.taxify.services.role;

import com.kts.taxify.exception.RoleNotFoundException;
import com.kts.taxify.model.Role;
import com.kts.taxify.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetRoleByName {
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Role execute(final String name) {
        return roleRepository.getByName(name).orElseThrow(RoleNotFoundException::new);
    }
}
