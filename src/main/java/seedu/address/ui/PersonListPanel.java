package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    private ProfileDetails profileDetails;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, ProfileDetails profileDetails) {
        super(FXML);
        this.profileDetails = profileDetails;
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        MultipleSelectionModel<Person> selectionPersonList = personListView.getSelectionModel();

        selectionPersonList.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.profileDetails.updateDetails(newValue);
        });

        personListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                selectionPersonList.clearSelection();
                this.profileDetails.updateDetails(null);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
