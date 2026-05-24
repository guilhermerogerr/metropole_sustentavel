package metropolesustentavel.model;

import java.util.ArrayList;
import java.util.List;

public class Indicador {
    private final TipoIndicador tipo;
    private double valor;
    private final double valorInicial;
    private final List<Double> historico;

    public Indicador(TipoIndicador tipo, double valorInicial) {
        this.tipo         = tipo;
        this.valor        = valorInicial;
        this.valorInicial = valorInicial;
        this.historico    = new ArrayList<>();
        this.historico.add(valorInicial);
    }

    public void ajustar(double delta) {
        this.valor = Math.max(0, Math.min(100, this.valor + delta));
        this.historico.add(this.valor);
    }

    public double getPercentualMelhora() {
        if (valorInicial == 0) return 0;
        if (tipo == TipoIndicador.POLUICAO || tipo == TipoIndicador.RESIDUOS)
            return ((valorInicial - valor) / valorInicial) * 100.0;
        return ((valor - valorInicial) / valorInicial) * 100.0;
    }

    public TipoIndicador getTipo()     { return tipo; }
    public double getValor()           { return valor; }
    public double getValorInicial()    { return valorInicial; }
    public List<Double> getHistorico() { return new ArrayList<>(historico); }
}
