package pt.galina.clientnonreactive.config.actuator.notes;

import lombok.Getter;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Endpoint(id="notes", enableByDefault=true)
// Custom actuator endpoint definition with id "notes" that will be enabled by default.
public class NotesEndpoint {

    // A list to store Note objects, representing a collection of "notes".
    private final List<Note> notes = new ArrayList<>();

    @ReadOperation
    // Marks this method to handle HTTP GET requests on /actuator/notes.
    public List<Note> notes() {
        // Returns the list of notes in response to the GET request.
        return notes;
    }

    @WriteOperation
    // Marks this method to handle HTTP POST or PUT requests on /actuator/notes.
    // The request must contain a parameter named 'text' to create a new note.
    public List<Note> addNote(String text) {
        // Adds a new note to the list using the provided 'text'.
        notes.add(new Note(text));
        // Returns the updated list of notes after adding the new one.
        return notes;
    }

    @DeleteOperation
    // Marks this method to handle HTTP DELETE requests on /actuator/notes.
    // The request must contain a parameter specifying the 'index' of the note to delete.
    public List<Note> deleteNote(int index) {
        // Checks if the provided index is valid (i.e., within the size of the list).
        if (index < notes.size()) {
            // Removes the note at the specified index.
            notes.remove(index);
        }
        // Returns the updated list of notes after deletion.
        return notes;
    }

    @Getter
    // The @Getter annotation from Lombok will automatically generate getter methods for all fields in this class.
    public static class Note {
        // A field to store the time when the note was created.
        private final Date time = new Date();

        // A field to store the text of the note.
        private final String text;

        // Constructor for creating a new Note object with the given text.
        public Note(String text) {
            this.text = text;
        }
    }
}
