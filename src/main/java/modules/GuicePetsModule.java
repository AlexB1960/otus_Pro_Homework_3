package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import pets.Pets;

public class GuicePetsModule extends AbstractModule {

  public GuicePetsModule() {
  }

  @Provides
  @Singleton
  public Pets getPet() {
    return new Pets();
  }

}
