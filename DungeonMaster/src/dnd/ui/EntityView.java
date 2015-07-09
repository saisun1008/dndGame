package dnd.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import dnd.game.Entity;
import dnd.game.InventoryInterface;
import dnd.game.entity.living.LivingEntity;

public class EntityView extends JPanel implements PropertyChangeListener {
	private static final long serialVersionUID = 350446592665155494L;
	private Entity entity;
	private JLabel entityInfoLabel;
	private InventoryView inventoryView;

	public EntityView(Entity entity) {
		Border border = new CompoundBorder(BorderFactory.createTitledBorder("Attributes"), 
				BorderFactory.createEmptyBorder(0, 6, 0, 6));
		this.entityInfoLabel = new JLabel("", JLabel.LEFT);
		this.entityInfoLabel.setAlignmentX(LEFT_ALIGNMENT);
		this.entityInfoLabel.setBorder(border);
		this.inventoryView = new InventoryView((InventoryInterface)entity);
		this.inventoryView.addPropertyChangeListener(this);
		this.inventoryView.setAlignmentX(LEFT_ALIGNMENT);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(entityInfoLabel);
		add(inventoryView);
		setEntity(entity);
	}
	
	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
		if (entity instanceof InventoryInterface) {
			inventoryView.setEntity((InventoryInterface)entity);
		}
		update();
	}

	public void update() {
		if (entity == null) {
			setVisible(false);
			return;
		}
		setVisible(true);
		setBorder(BorderFactory.createTitledBorder("Viewing: " + entity + " " + entity.getLocation()));
		if (entity instanceof InventoryInterface) {
			inventoryView.setVisible(true);
			inventoryView.update();
		}
		else {
			inventoryView.setVisible(false);
		}
		
		if (entity instanceof LivingEntity) {
			entityInfoLabel.setVisible(true);
			entityInfoLabel.setText("<html>" + 
					entity.formatModifiers().replace("\n", "<br>") + "</html>");
		}
		else {
			entityInfoLabel.setVisible(false);
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) { // pass property event changes up
		firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
	}
}
