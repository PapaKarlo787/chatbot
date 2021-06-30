import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.BiFunction;
import viewer.*;

class My3D extends Menu {
    private final Viewer viewer;
    public final HashMap<String, Function<String, Model>> modelLoad = new HashMap<>();
    public final HashMap<String, BiFunction<String, LinkedList<Model>, Integer>> modelSave = new HashMap<>();

    public My3D() {
        viewer = new Viewer();
        viewer.start();
        modelLoad.put("stl", Stl::load);
        modelLoad.put("obj", Obj::load);
        modelSave.put("stl", Stl::save);
        modelSave.put("obj", Obj::save);
    }
    
    @Override
    public void putHelps() {
        helps.add("Меню 3D\nПараметры для команд можно найти в README\nКоманды:");
        helps.add("    cube - нарисовать куб");
        helps.add("    tetrahedron - нарисовать тетраэдр");
        helps.add("    sphere - нарисовать сферу");
        helps.add("    color - изменить цвет текущего объекта");
        helps.add("    size - изменить размер текущего объекта");
        helps.add("    rotate - повернуть текущий объект");
        helps.add("    move - сменить позицию выделенного объекта");
        helps.add("    eye - установить камеру в позицию по умолчанию");
        helps.add("    next - выбрать следующий объект");
        helps.add("    prev - выбрать предыдущий объект");
        helps.add("    remove - удалить текущий объект");
        helps.add("    save - сохранить объект в файл");
        helps.add("    load - загрузить объект из файла");
    }

    private void floatX(String[] s, Consumer<Float[]> consumer) {
        consumer.accept(new Float[] { Float.parseFloat(s[0]), Float.parseFloat(s[1]), Float.parseFloat(s[2]) });
    }

    @Override
    public void exit() {
        viewer.exit();
    }

    private void loadModel(String name) {
        var names = name.split("\\.");
        viewer.modelsPublic.push(modelLoad.get(names[names.length - 1]).apply(name));
    }

    private void saveScene(String name) {
        var names = name.split("\\.");
        if (modelSave.get(names[names.length - 1]).apply(name, viewer.getModels()) != 0) {
            cl.print("Wrong file! Saving was canceled.");
        }
    }

    @Override
    public void putCommands() {
        commands.put("cube", s -> viewer.modelsPublic.push(new Cube()));
        commands.put("load", s -> loadModel(String.join(" ", s)));
        commands.put("tetrahedron", s -> viewer.modelsPublic.push(new Tetrahedron()));
        commands.put("sphere", s -> viewer.modelsPublic.push(new Sphere()));
        commands.put("next", s -> viewer.incSelection());
        commands.put("prev", s -> viewer.decSelection());
        commands.put("color", s -> viewer.getSelected().setColor(s));
        commands.put("size", s -> viewer.getSelected().size(Float.parseFloat(s[0])));
        commands.put("rotate", s -> floatX(s, x -> viewer.getSelected().rotate(x[0], x[1], x[2])));
        commands.put("move", s -> floatX(s, x -> viewer.getSelected().move(x[0], x[1], x[2])));
        commands.put("eye", s -> floatX(s, x -> Controller.setPosition(x[0], x[1], x[2])));
        commands.put("remove", s -> viewer.removeCurrent());
        commands.put("save", s -> saveScene(String.join(" ", s)));
    }
}
