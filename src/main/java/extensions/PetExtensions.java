package extensions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import modules.GuicePetsModule;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import pets.PetStore;

public class PetExtensions implements BeforeEachCallback, AfterEachCallback {
  private Injector injector = null;

  @Override
  public void beforeEach(ExtensionContext context) {
    injector = Guice.createInjector(new GuicePetsModule());
    injector.injectMembers(context.getTestInstance().get());
  }

  @Override
  public void afterEach(ExtensionContext context) {
    PetStore petStore = injector.getInstance(PetStore.class);

    while(!petStore.isEmpty()) {
      petStore.deletePetById(petStore.getPet());
    }
  }

}
