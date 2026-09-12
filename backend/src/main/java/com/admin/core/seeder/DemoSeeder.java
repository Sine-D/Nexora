package com.admin.core.seeder;

import com.admin.modules.developer.domain.DeveloperProfile;
import com.admin.modules.developer.domain.DeveloperSkill;
import com.admin.modules.developer.domain.ExperienceLevel;
import com.admin.modules.developer.infrastructure.DeveloperProfileRepository;
import com.admin.modules.developer.infrastructure.DeveloperSkillRepository;
import com.admin.modules.user.domain.Role;
import com.admin.modules.user.domain.User;
import com.admin.modules.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DemoSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DeveloperProfileRepository profileRepository;
    private final DeveloperSkillRepository skillRepository;

    @Override
    public void run(String... args) {
        seedUser("admin@nexora.com", "Admin User", Role.ADMIN, "admin123");
        seedUser("manager@nexora.com", "Manager User", Role.MANAGER, "manager123");
        User dev = seedUser("dev@nexora.com", "Developer User", Role.DEVELOPER, "dev123");

        if (dev != null) {
            seedDeveloperProfile(dev);
        }
    }

    private User seedUser(String email, String name, Role role, String rawPassword) {
        String normalizedEmail = email.trim().toLowerCase();
        if (userRepository.existsByEmail(normalizedEmail)) {
            return userRepository.findByEmail(normalizedEmail).orElse(null);
        }

        User user = User.builder()
                .email(normalizedEmail)
                .name(name)
                .role(role)
                .enabled(true)
                .password(passwordEncoder.encode(rawPassword))
                .build();

        userRepository.save(user);
        System.out.println("✅ Seeded " + role + " user: " + normalizedEmail);
        return user;
    }

    private void seedDeveloperProfile(User dev) {
        DeveloperProfile profile = profileRepository.findByUserId(dev.getId()).orElse(null);
        if (profile == null) {
            profile = profileRepository.save(DeveloperProfile.builder()
                    .user(dev)
                    .experienceLevel(ExperienceLevel.MID)
                    .capacityPoints(20)
                    .phone("")
                    .location("")
                    .bio("")
                    .build());
        }

        if (skillRepository.findByProfileId(profile.getId()).isEmpty()) {
            skillRepository.save(DeveloperSkill.builder().profile(profile).name("React").level(4).build());
            skillRepository.save(DeveloperSkill.builder().profile(profile).name("Node.js").level(4).build());
            skillRepository.save(DeveloperSkill.builder().profile(profile).name("Database").level(3).build());
        }
    }
}
