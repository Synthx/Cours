package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.Group;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class GroupServiceTest {

    @InjectMocks
    private GroupServiceJdbc groupService;

    @Mock
    private GroupRepository groupRepository;

    @Test
    public void create() {
        // When
        Mockito.when(groupRepository.findByName(Mockito.anyString())).thenReturn(null);

        // Test
        Group group = this.groupService.create("test");

        // Assert
        assertNotNull(group);
    }

    @Test
    public void delete() {
    }

    @Test
    public void findByName() {
    }

    @Test
    public void addRight() {
    }

    @Test
    public void removeRight() {
    }

    @Test
    public void findById() {
    }
}